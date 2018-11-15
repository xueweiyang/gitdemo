package fastdex.build.util

import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformInvocation
import fastdex.build.lib.snapshoot.api.DiffResultSet
import fastdex.build.variant.FastdexVariant
import fastdex.common.utils.FileUtils
import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.GradleException
import org.objectweb.asm.Opcodes
import java.nio.file.FileVisitResult
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

/**
 * jar操作
 * Created by tong on 17/11/4.
 */
class JarOperation implements Opcodes {
    static void generatePatchJar(FastdexVariant fastdexVariant, TransformInvocation transformInvocation, File patchJar) throws IOException {
        Set<LibDependency> libraryDependencies = fastdexVariant.getLibraryDependencies()
        Map<String,String> jarAndProjectPathMap = new HashMap<>()
        List<File> projectJarFiles = new ArrayList<>()
        //获取所有依赖工程的输出jar (compile project(':xxx'))
        for (LibDependency dependency : libraryDependencies) {
            projectJarFiles.add(dependency.jarFile)
            jarAndProjectPathMap.put(dependency.jarFile.absolutePath,dependency.dependencyProject.projectDir.absolutePath)
        }

        //所有的class目录
        Set<File> directoryInputFiles = new HashSet<>()
        //所有library工程输出的jar
        Set<File> jarInputFiles = new HashSet<>()
        for (TransformInput input : transformInvocation.getInputs()) {
            Collection<DirectoryInput> directoryInputs = input.getDirectoryInputs()
            if (directoryInputs != null) {
                for (DirectoryInput directoryInput : directoryInputs) {
                    directoryInputFiles.add(directoryInput.getFile())
                }
            }

            if (!projectJarFiles.isEmpty()) {
                Collection<JarInput> jarInputs = input.getJarInputs()
                if (jarInputs != null) {
                    for (JarInput jarInput : jarInputs) {
                        if (projectJarFiles.contains(jarInput.getFile())) {
                            jarInputFiles.add(jarInput.getFile())
                        }
                    }
                }
            }
        }

        def project = fastdexVariant.project
        File tempDir = new File(fastdexVariant.buildDir,"temp")
        FileUtils.deleteDir(tempDir)
        FileUtils.ensumeDir(tempDir)

        Set<File> moudleDirectoryInputFiles = new HashSet<>()
        DiffResultSet diffResultSet = fastdexVariant.projectSnapshoot.diffResultSet
        for (File file : jarInputFiles) {
            String projectPath = jarAndProjectPathMap.get(file.absolutePath)
            List<String> patterns = diffResultSet.addOrModifiedClassesMap.get(projectPath)
            if (patterns != null && !patterns.isEmpty()) {
                File classesDir = new File(tempDir,"${file.name}-${System.currentTimeMillis()}")
                project.copy {
                    from project.zipTree(file)
                    for (String pattern : patterns) {
                        include pattern
                    }
                    into classesDir
                }
                moudleDirectoryInputFiles.add(classesDir)
                directoryInputFiles.add(classesDir)
            }
        }
        JarOperation.generatePatchJar(fastdexVariant,directoryInputFiles,moudleDirectoryInputFiles,patchJar)
    }

    /**
     * 生成补丁jar,仅把变化部分参与jar的生成
     * @param project
     * @param directoryInputFiles
     * @param outputJar
     * @param changedClassPatterns
     * @throws IOException
     */
    static void generatePatchJar(FastdexVariant fastdexVariant, Set<File> directoryInputFiles,Set<File> moudleDirectoryInputFiles, File patchJar) throws IOException {
        long start = System.currentTimeMillis()
        def project = fastdexVariant.project
        project.logger.error("==fastdex generate patch jar start")

        if (directoryInputFiles == null || directoryInputFiles.isEmpty()) {
            throw new IllegalArgumentException("DirectoryInputFiles can not be null!!")
        }

        Set<String> changedClasses = fastdexVariant.projectSnapshoot.diffResultSet.addOrModifiedClasses
        if (fastdexVariant.configuration.hotClasses != null && fastdexVariant.configuration.hotClasses.length > 0) {
            String packageName = fastdexVariant.getOriginPackageName()
            for (String str : fastdexVariant.configuration.hotClasses) {
                if (str != null) {
                    changedClasses.add(str.replaceAll("\\{package\\}",packageName))
                }
            }
        }

        project.logger.error("==fastdex changedClasses: ${changedClasses}")

        if (changedClasses == null || changedClasses.isEmpty()) {
            throw new IllegalArgumentException("No java files changed!!")
        }

        FileUtils.deleteFile(patchJar)
        FileUtils.ensumeDir(patchJar.getParentFile())

        boolean willExeDexMerge = fastdexVariant.willExecDexMerge()

        ZipOutputStream outputJarStream = null
        try {
            outputJarStream = new ZipOutputStream(new FileOutputStream(patchJar))
            for (File classesDir : directoryInputFiles) {
                //fix library databinding bug
                if (!FileUtils.dirExists(classesDir.absolutePath)) {
                    continue
                }
                Path classpath = classesDir.toPath()

                //是否属于library工程的classes目录
                boolean libraryClassesDirectory = (moudleDirectoryInputFiles != null && moudleDirectoryInputFiles.contains(classesDir))

                //如果当前目录是主工程classes输出目录，并且是使用customJavac编译的
                Files.walkFileTree(classpath,new SimpleFileVisitor<Path>(){
                    @Override
                    FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        if (!file.toFile().getName().endsWith(Constants.CLASS_SUFFIX)) {
                            return FileVisitResult.CONTINUE
                        }
                        Path relativePath = classpath.relativize(file)
                        String entryName = relativePath.toString()
                        if (Os.isFamily(Os.FAMILY_WINDOWS)) {
                            entryName = entryName.replace("\\", "/")
                        }

                        if (libraryClassesDirectory) {
                            ZipEntry e = new ZipEntry(entryName)
                            outputJarStream.putNextEntry(e)
                            byte[] bytes = FileUtils.readContents(file.toFile())
                            //如果需要触发dex merge,必须注入代码
                            if (willExeDexMerge) {
                                project.logger.error("==fastdex prepare add injected class: ${e}")
                                bytes = ClassInject.inject(bytes)
                            }
                            else {
                                project.logger.error("==fastdex add class: ${e}")
                            }
                            outputJarStream.write(bytes,0,bytes.length)
                            outputJarStream.closeEntry()
                        }
                        else {
                            String className = relativePath.toString().substring(0,relativePath.toString().length() - Constants.CLASS_SUFFIX.length())
                            className = className.replaceAll(Os.isFamily(Os.FAMILY_WINDOWS) ? "\\\\" : File.separator,"\\.")

                            //假如发生变化的java文件是fastdex/sample/MainActivity.java， fastdex/sample/MainActivity.class和以fastdex/sample/MainActivity$开头的class会被加进去
                            int index = className.indexOf("\$")
                            if (index != -1) {
                                className = className.substring(0,index)
                            }
                            if (changedClasses.contains(className)) {
                                ZipEntry e = new ZipEntry(entryName)
                                outputJarStream.putNextEntry(e)

                                byte[] bytes = FileUtils.readContents(file.toFile())
                                //如果需要触发dex merge,必须注入代码
                                if (willExeDexMerge) {
                                    project.logger.error("==fastdex prepare add injected class: ${e}")
                                    bytes = ClassInject.inject(bytes)
                                }
                                else {
                                    project.logger.error("==fastdex add class: ${e}")
                                }
                                outputJarStream.write(bytes,0,bytes.length)
                                outputJarStream.closeEntry()
                            }
                        }
                        return FileVisitResult.CONTINUE
                    }
                })
            }

        } finally {
            if (outputJarStream != null) {
                outputJarStream.close()
            }
        }

        if (!FileUtils.isLegalFile(patchJar)) {
            throw new GradleException("==fastdex generate patch jar fail: ${patchJar}")
        }
        long end = System.currentTimeMillis()
        project.logger.error("==fastdex generate patch jar complete, use: ${end - start}ms \n${patchJar}")
    }
}
