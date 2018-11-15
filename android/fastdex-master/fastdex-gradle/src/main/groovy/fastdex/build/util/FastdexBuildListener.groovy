package fastdex.build.util

import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.BuildListener
import org.gradle.BuildResult
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.execution.TaskExecutionListener
import org.gradle.api.initialization.Settings
import org.gradle.api.invocation.Gradle
import org.gradle.api.tasks.TaskState
import fastdex.build.FastdexPlugin
import com.github.typ0520.fastdex.Version
import java.lang.management.ManagementFactory
import fastdex.common.utils.FileUtils

/**
 * Created by tong on 17/3/12.
 */
class FastdexBuildListener implements TaskExecutionListener, BuildListener {
    public static boolean skipReport = false

    private times = []
    private final Project project
    private long startMillis

    FastdexBuildListener(Project project) {
        this.project = project
    }

    @Override
    void beforeExecute(Task task) {
        startMillis = System.currentTimeMillis()
    }

    @Override
    void afterExecute(Task task, TaskState taskState) {
        times.add([System.currentTimeMillis() - startMillis, task.path])

        //task.project.logger.warn "${task.path} spend ${ms}ms"
    }

    @Override
    void buildStarted(Gradle gradle) {
    }

    @Override
    void projectsEvaluated(Gradle gradle) {}

    @Override
    void projectsLoaded(Gradle gradle) {}

    @Override
    void settingsEvaluated(Settings settings) {}

    @Override
    void buildFinished(BuildResult result) {
        if (result.failure == null) {
            println "Task spend time:"
            for (time in times) {
                if (time[0] >= 50) {
                    printf "%7sms  %s\n", time
                }
            }
        }
        else {
            if (project == null || !project.plugins.hasPlugin("com.android.application") || skipReport) {
                return
            }

            Throwable cause = getRootThrowable(result.failure)
            if (cause == null) {
                return
            }
            try {
                StackTraceElement[] stackTrace = cause.getStackTrace()
                if (!(cause instanceof FastdexRuntimeException)
                        && !(cause instanceof GradleException)
                        && stackTrace != null && stackTrace.length > 0
                        && stackTrace[0] != null
                        && stackTrace[0].toString().contains(FastdexPlugin.class.getPackage().getName())) {
                    File errorLogFile = new File(FastdexUtils.getBuildDir(project),Constants.ERROR_REPORT_FILENAME)

                    Map<String,String> map = getStudioInfo()

                    println("\n===========================fastdex error report===========================")
                    ByteArrayOutputStream bos = new ByteArrayOutputStream()
                    result.failure.printStackTrace(new PrintStream(bos))

                    String splitStr = "\n\n"
                    StringBuilder report = new StringBuilder()
                    //让android studio的Messages窗口显示打开Gradle Console的提示
                    report.append("Caused by: ----------------------------------fastdex---------------------------------\n")
                    report.append("Caused by: Open the Gradle Console in the lower right corner to view the build error report\n")
                    report.append("Caused by: ${errorLogFile}\n")
                    report.append("Caused by: ----------------------------------fastdex---------------------------------${splitStr}")
                    report.append("${new String(bos.toByteArray())}\n")

                    String str =  "Fastdex build version     "
                    report.append("Fastdex build version     : ${Version.FASTDEX_BUILD_VERSION}\n")
                    report.append("OS                        : ${getOsName()}\n")
                    report.append("android_build_version     : ${GradleUtils.getAndroidGradlePluginVersion()}\n")
                    report.append("gradle_version            : ${project.gradle.gradleVersion}\n")

                    report.append("buildToolsVersion         : ${project.android.buildToolsVersion.toString()}\n")
                    report.append("compileSdkVersion         : ${project.android.compileSdkVersion.toString()}\n")
                    report.append("default minSdkVersion     : ${project.android.defaultConfig.minSdkVersion.getApiString()}\n")
                    report.append("default targetSdkVersion  : ${project.android.defaultConfig.targetSdkVersion.getApiString()}\n")
                    report.append("default multiDexEnabled   : ${project.android.defaultConfig.multiDexEnabled}\n\n")
                    report.append("projectProperties         : ${project.gradle.startParameter.projectProperties}\n\n")

                    List<String> plugins = new ArrayList<>()
                    for (Plugin plugin : project.plugins.findAll()) {
                        if (!plugin.getClass().getName().startsWith("org.gradle")) {
                            plugins.add(plugin.getClass().getName())
                        }
                    }
                    report.append("plugins                   : ${plugins}\n\n")
                    try {
                        int keyLength = str.length()
                        if (!map.isEmpty()) {
                            for (String key : map.keySet()) {
                                int dsize = keyLength - key.length()
                                report.append(key + getSpaceString(dsize) + ": " + map.get(key) + "\n")
                            }

                            if (!"true".equals(map.get("instant_run_disabled"))) {
                                report.append("Fastdex does not support instant run mode, please disable instant run in 'File->Settings...'.\n\n")
                            }
                            else {
                                report.append("\n")
                            }
                        }
                    } catch (Throwable e) {
                        e.printStackTrace()
                    }

                    report.append("fastdex build exception, welcome to submit issue to us: https://github.com/typ0520/fastdex/issues")
                    System.err.println(report.toString())
                    System.err.println("${errorLogFile}")

                    int idx = report.indexOf(splitStr)
                    String content = report.toString()
                    if (idx != -1 && (idx + splitStr.length()) < content.length()) {
                        content = content.substring(idx + splitStr.length())
                    }
                    FileUtils.write2file(content.getBytes(),errorLogFile)
                    println("\n===========================fastdex error report===========================")
                }
            } catch (Throwable e) {

            }

            if (project.hasProperty("fastdex.injected.invoked.from.ide") && "org.gradle.execution.TaskSelectionException".equals(cause.class.name)) {
                String message = result.failure.getMessage()
                String[] arr = message.split("'")
                if (arr != null && arr.length > 1) {
                    String taskName = arr[1]

                    if (taskName.startsWith("fastdex")) {
                        System.err.println("")
                        System.err.println("If you modify buildTypes or productFlavors please synchronize build.gradle")
                    }
                }
            }
        }
    }

    String getOsName() {
        try {
            return System.getProperty("os.name").toLowerCase(Locale.ENGLISH)
        } catch (Throwable e) {

        }
        return ""
    }

    Throwable getRootThrowable(Throwable throwable) {
        if (throwable == null) {
            return null
        }
        Throwable cause = throwable.getCause()
        if (cause == null) {
            return throwable
        }
        if (cause == throwable) {
            return throwable
        }
        return getRootThrowable(throwable.getCause())
    }


    Map<String,String> getStudioInfo() {
        Map<String,String> map = new HashMap<>()
        if (Os.isFamily(Os.FAMILY_MAC)) {
            try {
                File script = new File(FastdexUtils.getBuildDir(project),String.format(Constants.STUDIO_INFO_SCRIPT_MACOS,Version.FASTDEX_BUILD_VERSION))
                if (!FileUtils.isLegalFile(script)) {
                    FileUtils.copyResourceUsingStream(Constants.STUDIO_INFO_SCRIPT_MACOS,script)
                }

                int pid = getPid()
                if (pid == -1) {
                    return map
                }

                List<String> cmdArgs = new ArrayList<>()
                cmdArgs.add("sh")
                cmdArgs.add(script.getAbsolutePath())
                cmdArgs.add("${pid}")

               FastdexUtils.runCommand(project,cmdArgs,true)
            } catch (Throwable e) {
                //e.printStackTrace()
            }
        }
        return map
    }

    static String getSpaceString(int count) {
        if (count > 0) {
            StringBuilder sb = new StringBuilder()
            for (int i = 0; i < count; i++) {
                sb.append(" ")
            }
            return sb.toString()
        }
        return ""
    }

    static int getPid() {
        String name = ManagementFactory.getRuntimeMXBean().getName()
        if (name != null) {
            String[] arr = name.split("@")
            try {
                return Integer.valueOf(arr[0])
            } catch (Throwable e) {

            }
        }
        return -1
    }

    static void addByProject(Project pro) {
        FastdexBuildListener listener = new FastdexBuildListener(pro)
        pro.gradle.addListener(listener)
    }
}