package com.example.systrace.transform

import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.Status
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformTask
import com.example.systrace.Log
import com.example.systrace.MethodCollector
import com.example.systrace.MethodTracker
import com.example.systrace.TraceBuildConfig
import com.example.systrace.item.TraceMethod
import com.example.systrace.retrace.MappingCollector
import com.example.systrace.retrace.MappingReader
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.execution.TaskExecutionGraph
import org.gradle.api.execution.TaskExecutionGraphListener

import java.lang.reflect.Field

public class SystemTraceTransform extends BaseProxyTransform {

    Transform originTransform
    Project project
    def variant

    SystemTraceTransform(Project project, def variant, Transform originTransform) {
        super(originTransform)
        this.originTransform = originTransform
        this.variant = variant
        this.project = project
    }

    public static void inject(Project project, def variant) {
        String hackTransformTaskName = getTransformTaskName("", "", variant.name)
        String hackTransformTaskNameForWrapper = getTransformTaskName("", "Builder", variant.name)

        project.getGradle().getTaskGraph().addTaskExecutionGraphListener(new TaskExecutionGraphListener() {
            @Override
            void graphPopulated(TaskExecutionGraph taskExecutionGraph) {
                for (Task task : taskExecutionGraph.getAllTasks()) {
                    if ((task.name.equalsIgnoreCase(hackTransformTaskName) || task.name.equalsIgnoreCase(hackTransformTaskNameForWrapper))
                            && !(((TransformTask) task).getTransform() instanceof SystemTraceTransform)) {
                        Field field = TransformTask.class.getDeclaredField("transform")
                        field.setAccessible(true)
                        field.set(task, new SystemTraceTransform(project, variant, task.transform))
                        break
                    }
                }
            }
        })
    }

    static String getTransformTaskName(String customDexTransformName, String wrappSuffix, String buildTypeSuffix) {
        if (customDexTransformName != null && customDexTransformName.length() > 0) {
            return "${customDexTransformName}For${buildTypeSuffix}"
        }
        return "transformClassesWithDex${wrappSuffix}For${buildTypeSuffix}"
    }

    @Override
    String getName() {
        return "SystemTraceTransform"
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        long start = System.currentTimeMillis()
        boolean isIncremental = transformInvocation.isIncremental() && this.isIncremental()
        File rootOutput = new File(project.systrace.output, "classes/${getName()}/")
        if (!rootOutput.exists()) {
            rootOutput.mkdirs()
        }
        TraceBuildConfig traceConfig = initConfig()
        MappingCollector mappingCollector = new MappingCollector()
        File mappingFile = new File(traceConfig.getMappingPath())
        if (mappingFile.exists() && mappingFile.isFile()) {
            MappingReader mappingReader = new MappingReader()
            mappingReader.read(mappingCollector)
        }

        Map<File, File> jarInputMap = new HashMap<>()
        Map<File, File> srcInputMap = new HashMap<>()

        transformInvocation.inputs.each { input ->
            input.directoryInputs.each { dirInput ->
                collectAndIdentifyDir(srcInputMap, dirInput, rootOutput, isIncremental)
            }
            input.jarInputs.each { jarInput ->
                if (jarInput.status != Status.REMOVED) {

                }
            }
        }

        MethodCollector methodCollector = new MethodCollector(traceConfig, mappingCollector)
        HashMap<String, TraceMethod> collectMethodMap = methodCollector.collect(srcInputMap.keySet().toList(),jarInputMap.keySet().toList())
        MethodTracker methodTracker = new MethodTracker(traceConfig, collectMethodMap, methodCollector.getCollectClassExtendMap())
        methodTracker.trace(srcInputMap, jarInputMap)
        originTransform.transform(transformInvocation)
    }

    void collectAndIdentifyDir(Map<File, File> dirInputMap, DirectoryInput input, File rootOutput, boolean isIncremental) {
        File dirInput = input.file
        File dirOutput = new File(rootOutput, dirInput.getName())
        if (!dirOutput.exists()) {
            dirOutput.mkdirs()
        }
        println("--------input:${dirInput.absolutePath} out:${dirOutput.absolutePath}")
        if (isIncremental) {
            if (!dirInput.exists()) {
                dirOutput.deleteDir()
            } else {
                Map<File, Status> obfuscatedChangedFiles = new HashMap<>()
                String rootInputFullPath = dirInput.absolutePath
                String rootOutputFullPath = dirOutput.absolutePath
                input.changedFiles.each { entry ->
                    File changedFileInput = entry.key
                    String changedFileInputPath = changedFileInput.absolutePath
                    File changedFileOutput = new File(
                            changedFileInputPath.replace(rootInputFullPath, rootOutputFullPath)
                    )
                    Status status = entry.value
                    switch (status) {
                        case Status.NOTCHANGED:
                            break
                        case Status.ADDED:
                        case Status.CHANGED:
                            dirInputMap.put(changedFileInput, changedFileOutput)
                            break
                        case Status.REMOVED:
                            changedFileOutput.delete()
                            break
                    }
                    obfuscatedChangedFiles.put(changedFileOutput, status)
                    println("------------changefileoutput:${changedFileOutput.absolutePath} status:${status}")
                }
                replaceChangedFile(input, obfuscatedChangedFiles)
            }
        } else {
            dirInputMap.put(dirInput, dirOutput)
        }
        replaceFile(input, dirOutput)
    }

    TraceBuildConfig initConfig() {
        def configuration = project.systrace
        def variantName = variant.name.capitalize()
        def mappingFilePath = ""
        if (variant.getBuildType().isMinifyEnabled()) {
            mappingFilePath = variant.mappingFile.getAbsolutePath()
        }
        TraceBuildConfig traceBuildConfig = new TraceBuildConfig.Builder()
                .setPackageName(variant.applicationId)
                .setBaseMethodMap(configuration.baseMethodMapFile)
                .setMethodMapFile(configuration.output + "/${variantName}.methodmap")
                .setIgnoreMethodMapFile(configuration.output + "/${variantName}.ignoremethodmap")
                .setBlackListDir(configuration.blackListFile)
                .setMappingPath(mappingFilePath)
                .build()
        return traceBuildConfig
    }
}