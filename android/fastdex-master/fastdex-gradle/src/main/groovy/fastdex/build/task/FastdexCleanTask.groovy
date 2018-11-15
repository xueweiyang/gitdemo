package fastdex.build.task

import fastdex.build.util.FastdexUtils
import fastdex.build.variant.FastdexVariant
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * 清空指定variantName的缓存，如果variantName == null清空所有缓存
 * Created by tong on 17/3/12.
 */
class FastdexCleanTask extends DefaultTask {
    FastdexVariant fastdexVariant

    FastdexCleanTask() {
        group = 'fastdex'
    }

    @TaskAction
    def clean() {
        if (fastdexVariant == null) {
            FastdexUtils.cleanAllCache(project)
        }
        else {
            FastdexUtils.cleanCache(project,variantName)
        }
    }
}
