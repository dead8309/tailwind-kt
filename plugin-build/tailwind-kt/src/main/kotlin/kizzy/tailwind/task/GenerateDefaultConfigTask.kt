@file:Suppress("LeakingThis")

package kizzy.tailwind.task

import kizzy.tailwind.extension.TailwindPluginExtension
import kizzy.tailwind.templates.cssTemplate
import kizzy.tailwind.templates.postCssConfigTemplate
import kizzy.tailwind.templates.postCssLoaderTemplate
import kizzy.tailwind.templates.tailwindTemplate
import org.gradle.api.DefaultTask
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import java.io.File
import javax.inject.Inject

abstract class GenerateDefaultConfigTask @Inject constructor(
    extension: TailwindPluginExtension
) : DefaultTask() {

    @get:Input
    @get:Option(option = "configDir", description = DESCRIPTION)
    abstract val configsDir: Property<File>

    init {
        configsDir.set(extension.configsDir)
        description = "Generates default config files if they are not present in project directory"
        group = BasePlugin.BUILD_GROUP
    }

    @TaskAction
    fun run() {
        logger.debug("Using configDir: {}", configsDir.get())
        check(configsDir.get().exists())
        val configsDir = configsDir.get()

        val tailwindConfig = File(configsDir, "tailwind.config.js")
        val postCssConfig = File(configsDir, "postcss.config.js")

        writeToFile("Tailwind", tailwindConfig, tailwindTemplate())
        writeToFile("Postcss", postCssConfig, postCssConfigTemplate())

        val webpack = project.projectDir.resolve("webpack.config.d")
        webpack.mkdirs()
        val postCssLoader = File(webpack, "postcss-loader.config.js")
        writeToFile("Postcss Loader", postCssLoader, postCssLoaderTemplate())

        val cssFile = File(project.projectDir.resolve("src/jsMain/resources"), "globals.css")
        writeToFile("Css", cssFile, cssTemplate())
    }

    private fun writeToFile(
        tag: String,
        file: File,
        content: String
    ) {
        if (file.exists()) {
            logger.debug("Skipping default $tag setup as ${file.name} already exists")
        } else {
            file.writeText(content)
            logger.info("Finished $tag setup, ${file.name} is located in $file")
        }
    }

    internal companion object {
        private const val DESCRIPTION =
            "Location of a directory where default tailwind.config.js & postcss.config.js should be generated. " +
                "Default is projectDir"
        const val NAME = "generateDefaultFiles"
    }
}
