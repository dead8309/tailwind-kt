@file:Suppress("LeakingThis")

package kizzy.tailwind.extension

import org.gradle.api.Project
import org.gradle.api.provider.Property
import java.io.File
import javax.inject.Inject

abstract class TailwindPluginExtension @Inject constructor(project: Project) {
    internal companion object {
        const val NAME = "tailwind"
    }

    abstract val configsDir: Property<File>

    abstract val moduleName: Property<String>
    init {
        configsDir.convention(project.projectDir)
        moduleName.convention(project.rootProject.name)
    }
}
