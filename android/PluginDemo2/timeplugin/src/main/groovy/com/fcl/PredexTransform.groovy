package com.fcl

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.fcl.asm.AsmInject
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.gradle.api.Project

public class PredexTransform extends Transform{

    Project project

    public PredexTransform(Project project) {
        this.project =project
        def libPath = project.project(':hack').buildDir.absolutePath.concat("/intermediates/classes/debug")
        Inject.appendClassPath(libPath)
        Inject.appendClassPath("/home/fcl/Android/Sdk/platforms/android-28/android.jar")
    }

    @Override
    String getName() {
        return "PredexPlugin"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return true
    }

    @Override
    void transform(Context context, Collection<TransformInput> inputs, Collection<TransformInput> referencedInputs,
        TransformOutputProvider outputProvider, boolean isIncremental)
        throws IOException, TransformException, InterruptedException {
        inputs.each { TransformInput input->
            input.directoryInputs.each {DirectoryInput directoryInput->
//                println("filepath:"+directoryInput.file.absolutePath)
//                Inject.injectDir(directoryInput.file.absolutePath)
                AsmInject.injectDir(directoryInput.file.absolutePath)


                def dest=outputProvider.getContentLocation(directoryInput.name,
                directoryInput.contentTypes,directoryInput.scopes,Format.DIRECTORY)
//                println("dest:"+dest)
                FileUtils.copyDirectory(directoryInput.file,dest)
            }
            input.jarInputs.each {JarInput jarInput->
//                println("---jarinput file---" + jarInput.file.absolutePath)
                String jarPath = jarInput.file.absolutePath
                String projectName = project.rootProject.name
                if (jarPath.endsWith("classes.jar")
                && jarPath.contains("exploded-aar\\"+projectName)
                && !jarPath.contains("exploded-aar\\"+projectName+"\\hotpatch")) {
                    Inject.injectJar(jarPath)
                }
                def dest = outputProvider.getContentLocation(jarInput.name,jarInput.contentTypes,jarInput.scopes,
                    Format.JAR)
                FileUtils.copyFile(jarInput.file,dest)
            }

        }
    }
}
