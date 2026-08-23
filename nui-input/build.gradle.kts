plugins {
    `java-library`
    `maven-publish`
    id("ru.vyarus.animalsniffer")
}

apply(from = "$rootDir/gradle/common.gradle.kts")

val jomlVersion = rootProject.extra["jomlVersion"] as String
val geomVersion = rootProject.extra["geomVersion"] as String

dependencies {
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

    implementation(group = "com.google.guava", name = "guava", version = "23.0")

    // Same Android-suitability check as gestalt's gestalt-library-common.gradle.kts.
    signature("com.toasttab.android:gummy-bears-api-24:0.15.0:coreLib2@signature")
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
