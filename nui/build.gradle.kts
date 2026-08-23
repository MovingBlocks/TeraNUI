plugins {
    `java-library`
    `maven-publish`
    id("ru.vyarus.animalsniffer")
}

apply(from = "$rootDir/gradle/common.gradle.kts")

val jomlVersion = rootProject.extra["jomlVersion"] as String
val geomVersion = rootProject.extra["geomVersion"] as String

dependencies {
    api(group = "org.abego.treelayout", name = "org.abego.treelayout.core", version = "1.0.3")
    api(group = "com.miglayout", name = "miglayout-core", version = "5.0")

    api("org.joml:joml") {
        version {
            require(jomlVersion)
        }
    }

    api("org.terasology.joml-ext:joml-geometry") {
        version {
            require(geomVersion)
        }
    }

    api(project(":nui-input"))
    api(project(":nui-reflect"))

    implementation(group = "com.google.code.gson", name = "gson", version = "2.6.2")
    // The org.terasology fork of reflections drops the javax.servlet/slf4j-simple optional deps
    // upstream still carries - gestalt-module already depends on it, and mixing it with plain
    // upstream reflections here caused an Android duplicate-class conflict (both jars declare the
    // same org.reflections.* classes) that only surfaces once something actually dexes both.
    implementation(group = "org.terasology", name = "reflections", version = "0.9.12-MB")
    implementation("org.slf4j:slf4j-api:1.7.30")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.6.2")
    testImplementation("junit:junit:4.12")

    // Same Android-suitability check as gestalt's gestalt-library-common.gradle.kts.
    signature("com.toasttab.android:gummy-bears-api-24:0.15.0:coreLib2@signature")
}

tasks.test {
    useJUnitPlatform()
}

animalsniffer {
    // java.nio.* APIs can be desugared by D8. java.io.File.toPath() also needs to be excluded.
    //
    // java.awt.Toolkit/datatransfer.* genuinely don't exist on Android at all - AwtClipboardProvider
    // (UIText's pluggable, but default, ClipboardProvider) isolates that usage behind a
    // LinkageError-catching caller so it degrades gracefully instead of crashing, but the check
    // itself can't be satisfied for a real platform absence like this one, so it's excluded here
    // rather than by leaving the whole check non-fatal.
    ignore = listOf("java.nio.file.*", "java.io.File", "java.awt.Toolkit", "java.awt.datatransfer.*")
}
