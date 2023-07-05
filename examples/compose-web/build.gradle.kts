import kizzy.tailwind.utils.setupTailwindProject

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
    id("io.github.dead8309.tailwind-kt")
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

tailwind {
    /** This is required here because we are in a multimodule project. When we run this project
     * we will get our build module located at {rootDir}/build/js/packages/{rootProjectName}-{moduleName}.
     *
     * If we were in a simple KotlinJs project then your build module will be your rootProject name,
     * which is the default value of moduleName.
     */
    moduleName.set("${rootProject.name}-${project.name}")
}

kotlin {
    js(IR) {
        browser {}
        binaries.executable()
    }
    setupTailwindProject()
    @Suppress("UNUSED_VARIABLE")
    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(compose.html.core)
                implementation(compose.runtime)
            }
        }
    }
}
