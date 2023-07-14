@file:Suppress("LeakingThis")

package kizzy.tailwind.task

import kizzy.tailwind.extension.TailwindPluginExtension
import org.gradle.api.DefaultTask
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.getting
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsSetupTask
import org.jetbrains.kotlin.gradle.targets.js.npm.tasks.KotlinNpmInstallTask
import java.io.File
import javax.inject.Inject

abstract class CopyConfigsTask @Inject constructor(
    extension: TailwindPluginExtension,
    task: GenerateDefaultConfigTask
) : DefaultTask() {

    @get:Input
    @get:Option(option = "configDir", description = DESCRIPTION)
    abstract val configDir: Property<File>

    @get:Input
    @get:Option(option = "moduleName", description = ModuleName_DESCRIPTION)
    abstract val moduleName: Property<String>

    init {
        configDir.set(extension.configsDir)
        moduleName.set(extension.moduleName)
        group = BasePlugin.BUILD_GROUP
        description = "Copies tailwind,postcss files to build directory"
        val kotlinNodeJsSetup by project.rootProject.tasks.getting(NodeJsSetupTask::class)
        val kotlinNpmInstall by project.rootProject.tasks.getting(KotlinNpmInstallTask::class)
        dependsOn(kotlinNodeJsSetup, kotlinNpmInstall, task)
    }

    @TaskAction
    fun run() {
        val jsWorkspace = "${project.rootProject.buildDir}/js"
        val jsProjectDir = "$jsWorkspace/packages/${moduleName.get()}"

        val tailwindConfig = File(configDir.get(), "tailwind.config.js")
        val postCssConfig = File(configDir.get(), "postcss.config.js")

        tailwindConfig.copyTo(File(jsProjectDir, tailwindConfig.name), overwrite = true)
        postCssConfig.copyTo(File(jsProjectDir, postCssConfig.name), overwrite = true)
    }

    internal companion object {
        const val NAME = "copyConfigFiles"
        private const val DESCRIPTION =
            "Location of a directory containing tailwind.config.js & postcss.config.js. Default is projectDir"
        private const val ModuleName_DESCRIPTION =
            "The name you'd like to use to represent this project. The final JS output file uses this name. " +
                "Default is project.rootProject.name"
    }
}
