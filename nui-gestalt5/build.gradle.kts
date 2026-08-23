plugins {
    `java-library`
    `maven-publish`
    id("ru.vyarus.animalsniffer")
}

apply(from = "$rootDir/gradle/common.gradle.kts")

dependencies {
    api(group = "org.terasology", name = "gestalt-module", version = "5.1.5")
    api(group = "org.terasology", name = "gestalt-asset-core", version = "5.1.5")

    implementation("org.slf4j:slf4j-api:1.7.30")
    // See nui/build.gradle.kts for why this is the org.terasology fork, not upstream.
    implementation(group = "org.terasology", name = "reflections", version = "0.9.12-MB")
    implementation(group = "com.google.code.gson", name = "gson", version = "2.6.2")
    implementation(group = "com.google.guava", name = "guava", version = "23.0")

    api(project(":nui"))
    api(project(":nui-reflect"))

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
