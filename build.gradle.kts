import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URI
import java.time.LocalDate

plugins {
    kotlin("jvm").version("2.3.10")
    id("org.jetbrains.dokka").version("2.1.0")
    id("com.vanniktech.maven.publish") version "0.34.0"
}

group = "group.phorus"
description = "Exception hierarchy with HTTP status codes and error codes for Kotlin/JVM services."
version = "1.0.1"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    withSourcesJar()
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
}

configurations.configureEach {
    resolutionStrategy.eachDependency {
        if (requested.group.startsWith("com.fasterxml.jackson")) {
            useVersion("2.18.6")
        }
        if (requested.group == "org.bouncycastle") {
            useVersion("1.84")
        }
    }
}

val repoUrl = System.getenv("GITHUB_REPOSITORY")?.let { "https://github.com/$it" }
    ?: "https://github.com/phorus-group/exception-core"

tasks {
    withType<KotlinCompile> {
        compilerOptions {
            jvmTarget.set(JvmTarget.fromTarget(java.targetCompatibility.toString()))
        }
    }

    dokka {
        val branch = System.getenv("GITHUB_REF_NAME") ?: "main"
        val currentYear = LocalDate.now().year

        dokkaPublications.html {
            outputDirectory.set(layout.buildDirectory.dir("dokka/html"))
        }

        dokkaSourceSets.configureEach {
            reportUndocumented.set(true)
            jdkVersion.set(java.targetCompatibility.majorVersion.toInt())
            sourceRoots.from(file("src"))

            sourceLink {
                localDirectory.set(file("src/main/kotlin"))
                remoteUrl.set(URI("$repoUrl/tree/$branch/src/main/kotlin"))
                remoteLineSuffix.set("#L")
            }
        }

        pluginsConfiguration.html {
            footerMessage.set("© $currentYear Phorus Group - Licensed under the <a target=\"_blank\" href=\"$repoUrl/blob/$branch/LICENSE\">Apache 2 license</a>.")
        }
    }
}

afterEvaluate {
    tasks.named("generateMetadataFileForMavenPublication") {
        dependsOn("dokkaJavadocJar")
    }
}

mavenPublishing {
    coordinates(
        groupId = project.group.toString(),
        artifactId = project.name,
        version = project.version.toString()
    )

    pom {
        name.set(project.name)
        description.set(project.description ?: "")
        url.set(repoUrl)

        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }

        developers {
            developer {
                id.set("irios.phorus")
                name.set("Martin Rios")
                email.set("irios@phorus.group")
                organization.set("Phorus Group")
                organizationUrl.set("https://phorus.group")
            }
        }

        scm {
            url.set(repoUrl)
            connection.set("scm:git:$repoUrl.git")
            developerConnection.set("scm:git:$repoUrl.git")
        }
    }

    publishToMavenCentral(automaticRelease = true)
    signAllPublications()
}
