package kizzy.tailwind

import kizzy.tailwind.extension.TailwindPluginExtension
import kizzy.tailwind.task.CopyConfigsTask
import kizzy.tailwind.task.GenerateDefaultConfigTask
import org.gradle.api.Plugin
import org.gradle.api.Project

private const val COMPILE_KOTLIN_JS = "compileKotlinJs"

class TailwindPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            val extension = extensions.create(
                TailwindPluginExtension.NAME,
                TailwindPluginExtension::class.java,
                project
            )

            val generateDefaultConfigTask = tasks.register(
                GenerateDefaultConfigTask.NAME,
                GenerateDefaultConfigTask::class.java,
                extension
            )
            afterEvaluate {
                val copyConfigsTask = tasks.register(
                    CopyConfigsTask.NAME,
                    CopyConfigsTask::class.java,
                    extension,
                    generateDefaultConfigTask.get()
                )
                tasks.named(COMPILE_KOTLIN_JS) {
                    dependsOn(copyConfigsTask)
                }
            }
        }
    }
}
