// Copyright 2020 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

// Not a real application - exists purely to force the real Android toolchain (javac -> D8
// desugar/dex -> APK assembly) to process nui/nui-libgdx's compiled classes end-to-end, verifying
// they're genuinely buildable for Android rather than just API-signature-clean. gradle/common.gradle.kts's
// AnimalSniffer check only verifies referenced java.* symbols exist on Android - it says nothing
// about whether the resulting class files actually dex, which is what this module checks instead.
// Only included when a local Android SDK is configured - see settings.gradle.kts.
plugins {
    id("com.android.application")
}

// Not covered by gradle/common.gradle.kts (that's only applied by the plain java-library subprojects) -
// needs its own repositories to resolve nui/nui-libgdx's own dependencies (gestalt, libGDX, ...).
repositories {
    google()
    mavenCentral()

    maven {
        name = "Terasology Artifactory"
        url = uri("https://artifactory.terasology.io/artifactory/virtual-repo-live")
    }
}

android {
    namespace = "org.terasology.nui.android.verify"
    compileSdk = 34

    defaultConfig {
        applicationId = "org.terasology.nui.android.verify"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    // nui/nui-reflect/nui-gestalt5/nui-gestalt7 all consistently depend on the org.terasology fork
    // of reflections now (see nui/build.gradle.kts), so that duplicate-class conflict is fixed at
    // the source rather than excluded here. com.google.code.findbugs:annotations vs :jsr305 (both
    // declare javax.annotation.*) is still a real, separate conflict in the transitive dependency
    // graph - Gradle's plain "highest version wins" resolution never catches this since it doesn't
    // consider class *contents*, only Android's stricter checkDuplicateClasses task does. Any real
    // Android app depending on nui-libgdx would need the same exclusion.
    implementation(project(":nui"))
    implementation(project(":nui-libgdx")) {
        exclude(group = "com.google.code.findbugs", module = "annotations")
    }
}
