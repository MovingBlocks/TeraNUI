plugins {
    `java-library`
    `maven-publish`
    id("ru.vyarus.animalsniffer")
}

apply(from = "$rootDir/gradle/common.gradle.kts")

val gdxVersion = "1.9.14"
val roboVMVersion = "2.3.3"

dependencies {
    api(project(":nui"))
    api(project(":nui-input"))
    api(project(":nui-gestalt7"))

    api("com.badlogicgames.gdx:gdx:$gdxVersion")

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
