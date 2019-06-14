package com.example.systrace.transform

import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformTask
import com.example.systrace.TraceBuildConfig
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
        if (mappingFile.exists() && mappingFile.isFile()){
            MappingReader mappingReader=new MappingReader()
        }
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