package kizzy.tailwind

import kizzy.tailwind.extension.TailwindPluginExtension
import kizzy.tailwind.task.GenerateDefaultConfigTask
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class TailwindPluginTest {

    @Test
    fun `plugin is applied correctly to the project`() {
        val project = ProjectBuilder.builder().build()
        project.pluginManager.apply("io.github.dead8309.tailwind-kt")

        assert(project.tasks.getByName("generateDefaultFiles") is GenerateDefaultConfigTask)
    }

    @Test
    fun `extension tailwind is created correctly`() {
        val project = ProjectBuilder.builder().build()
        project.pluginManager.apply("io.github.dead8309.tailwind-kt")

        assertNotNull(project.extensions.getByName("tailwind"))
    }

    @Test
    fun `parameters are passed correctly from extension to task`() {
        val project = ProjectBuilder.builder().build()
        project.pluginManager.apply("io.github.dead8309.tailwind-kt")
        (project.extensions.getByName("tailwind") as TailwindPluginExtension).apply {
            configsDir.set(project.projectDir)
        }

        val task = project.tasks.getByName("generateDefaultFiles") as GenerateDefaultConfigTask

        assertEquals(project.projectDir, task.configsDir.get())
    }
}
