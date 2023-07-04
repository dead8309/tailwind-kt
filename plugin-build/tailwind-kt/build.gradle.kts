@file:Suppress("UnstableApiUsage")

plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    `maven-publish`
    signing
}

dependencies {
    // Get access to Kotlin multiplatform source sets
    implementation(kotlin("gradle-plugin"))
    testImplementation(libs.junit)
}

gradlePlugin {
    plugins {
        create(property("ID").toString()) {
            id = property("ID").toString()
            implementationClass = property("IMPLEMENTATION_CLASS").toString()
            version = property("VERSION").toString()
            description = property("DESCRIPTION").toString()
            displayName = property("DISPLAY_NAME").toString()
            tags.set(listOf("kotlinjs", "tailwindcss"))
            website.set(property("WEBSITE").toString())
            vcsUrl.set(property("VCS_URL").toString())
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenPluginPublish") {
            from(components["java"])
            artifactId = "tailwind-kt"
            pom {
                name.set(property("DISPLAY_NAME").toString())
                description.set(property("DISPLAY_NAME").toString())
                url.set(property("WEBSITE").toString())
                licenses {
                    license {
                        name.set("MIT License")
                    }
                }
                developers {
                    developer {
                        id.set("dead8309")
                        name.set("Vaibhav Raj")
                        email.set("deadyt8309@gmail.com")
                    }
                }
            }
        }
    }
    repositories {
        maven {
            val releasesRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2")
            val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots")
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
            credentials {
                username = System.getenv("OSSRH_USERNAME")
                password = System.getenv("OSSRH_PASSWORD")
            }
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications["mavenPluginPublish"])
}
