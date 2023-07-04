@file:Suppress("UnstableApiUsage")

import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication
import kizzy.tailwind.utils.setupTailwindProject

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kobweb.application)
    id("io.github.dead8309.tailwind-kt")
}

group = "org.example"
version = "1.0.0"


kobweb {
    app {
        index {
            description.set("Powered by Kobweb")
        }
    }
}

tailwind {
    moduleName.set("examples-kobweb")
}

kotlin {
    configAsKobwebApplication("examples-kobweb",includeServer = false)
    setupTailwindProject()

    @Suppress("UNUSED_VARIABLE") // Suppress spurious warnings about sourceset variables not being used
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(compose.html.core)
                implementation(libs.kobweb.core)
                implementation(libs.kobweb.silk.core)
            }
        }
    }
}
