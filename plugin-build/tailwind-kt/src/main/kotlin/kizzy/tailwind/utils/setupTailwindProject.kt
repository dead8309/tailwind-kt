package kizzy.tailwind.utils

import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.getting
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension


fun KotlinMultiplatformExtension.setupTailwindProject(skipDependencies: Boolean = false) {
    js(IR) {
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
        }
    }
    @Suppress("UNUSED_VARIABLE")
    val jsMain by sourceSets.getting {
        dependencies {
            if (!skipDependencies) {
                implementation(devNpm("tailwindcss", "3.3.2"))
                implementation(devNpm("postcss", "8.4.8"))
                implementation(devNpm("autoprefixer", "10.4.2"))
                implementation(devNpm("postcss-loader", "4.3.0"))
            }
            // This will always be added as we need kotlinext.js.require("./globals.css") from it
            implementation("org.jetbrains.kotlin-wrappers:kotlin-extensions:1.0.1-pre.256-kotlin-1.5.31")
        }
    }
}

