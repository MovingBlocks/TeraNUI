// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.javadoc.Javadoc
import org.gradle.api.tasks.testing.Test
import org.gradle.authentication.http.BasicAuthentication

// Applied via apply(from = ...) into each subproject's build script, so it must use the explicit
// configure<T> form (script plugins don't get the generated type-safe accessors a project's own
// primary build script does) - same approach as CrashReporter's own gradle/common.gradle.kts. That
// works for Gradle's own org.gradle.api.* types (always on every script's compile classpath), but
// NOT for third-party plugin classpath dependencies like ru.vyarus's AnimalSniffer - importing that
// extension type here fails to resolve even though the classpath is declared in root's buildscript
// {} block, because apply(from=)-loaded scripts don't inherit that classpath for their own Kotlin
// compilation. So AnimalSniffer's plugin application + extension configuration live in each
// subproject's own primary build.gradle.kts instead (a real, if small, duplication cost) - see any
// of those files for the actual ignore-list configuration.
val nuiVersion = rootProject.extra["nuiVersion"] as String

configure<JavaPluginExtension> {
    withSourcesJar()
    withJavadocJar()

    // Matches gestalt's gestalt-library-common.gradle.kts.
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

// Gradle 9 (unlike 8.2.1, which this wrapper was previously on) no longer pulls the JUnit Platform
// launcher onto the test runtime classpath transitively from junit-jupiter-engine - every
// subproject here uses useJUnitPlatform() below, so needs this declared explicitly or `test` fails
// immediately with "Failed to load JUnit Platform".
dependencies {
    // 1.6.2 to match the junit-jupiter-engine:5.6.2 subprojects declare - JUnit Platform and Jupiter
    // versions are paired (5.x.y <-> platform 1.x.y).
    add("testRuntimeOnly", "org.junit.platform:junit-platform-launcher:1.6.2")
}

// AnimalSniffer (the Android-suitability check) is applied and configured per-subproject instead
// of here - see the top-of-file note on why.

// We use both Maven Central and our own Artifactory instance, which contains module builds, extra libs, and so on
repositories {
    // For development so you can publish binaries locally and have them grabbed from there
    mavenLocal()

    // External libs
    mavenCentral()

    // Used for gestalt 7 - Android annotations
    google()

    // Terasology Artifactory instance for libs not readily available elsewhere plus our own libs
    maven {
        val repoViaEnv = System.getenv("RESOLUTION_REPO")
        if (rootProject.hasProperty("alternativeResolutionRepo")) {
            // If the user supplies an alternative repo via gradle.properties then use that
            name = "from alternativeResolutionRepo property"
            url = uri(rootProject.property("alternativeResolutionRepo") as String)
        } else if (!repoViaEnv.isNullOrEmpty()) {
            name = "from \$RESOLUTION_REPO"
            url = uri(repoViaEnv)
        } else {
            // Our default is the main virtual repo containing everything except repos for testing Artifactory itself
            name = "Terasology Artifactory"
            url = uri("https://artifactory.terasology.io/artifactory/virtual-repo-live")
        }
    }

    maven {
        name = "snowplow (pre-0.9)"
        url = uri("http://maven.snplow.com/releases")
        isAllowInsecureProtocol = true // 😱
    }
}

// Extra details provided for unit tests
tasks.named<Test>("test") {
    useJUnitPlatform()

    // ignoreFailures: Specifies whether the build should break when the verifications performed by this task fail.
    ignoreFailures = true
    // showStandardStreams: makes the standard streams (err and out) visible at console when running tests
    testLogging.showStandardStreams = true
    reports {
        junitXml.required.set(true)
    }
    // Arguments to include while running tests
    jvmArgs("-Xms512m", "-Xmx1024m")
}

// In theory all Javadoc should be good and fixed, but it might be a bit much to entirely fail a build over. For now at least ...
// Note: In IntelliJ 2020.1+ running a javadoc Gradle task may still *look* alarming in the UI, but errors should be ignored
tasks.named<Javadoc>("javadoc") {
    isFailOnError = false
}

group = "org.terasology.nui"
version = nuiVersion

configure<PublishingExtension> {
    publications {
        create<MavenPublication>(project.name) {
            // Without this we get a .pom with no dependencies
            from(components["java"])

            repositories {
                maven {
                    name = "TerasologyOrg"

                    if (rootProject.hasProperty("publishRepo")) {
                        // This first option is good for local testing, you can set a full explicit target repo in gradle.properties
                        val publishRepo = rootProject.property("publishRepo")
                        url = uri("https://artifactory.terasology.io/artifactory/$publishRepo")

                        logger.info("Changing PUBLISH repoKey set via Gradle property to {}", publishRepo)
                    } else {
                        // Support override from the environment to use a different target publish org
                        var deducedPublishRepo = System.getenv("PUBLISH_ORG").takeIf { !it.isNullOrEmpty() } ?: "libs"

                        // Base final publish repo on whether we're building a snapshot or a release
                        deducedPublishRepo += if (project.version.toString().endsWith("SNAPSHOT")) {
                            "-snapshot-local"
                        } else {
                            "-release-local"
                        }

                        logger.info("The final deduced publish repo is {}", deducedPublishRepo)
                        url = uri("https://artifactory.terasology.io/artifactory/$deducedPublishRepo")
                    }

                    if (rootProject.hasProperty("mavenUser") && rootProject.hasProperty("mavenPass")) {
                        credentials {
                            username = rootProject.property("mavenUser") as String
                            password = rootProject.property("mavenPass") as String
                        }
                        authentication {
                            create<BasicAuthentication>("basic")
                        }
                    }
                }
            }
        }
    }
}
