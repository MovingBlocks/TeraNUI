// Copyright 2021 The Terasology Foundation
// SPDX-License-Identifier: Apache-2.0

// See gradle/common.gradle.kts for more or less global build logic applied to the subprojects

// Only needed for Android suitability checking (AnimalSniffer, see gradle/common.gradle.kts) plus
// any future Android-specific module, same as gestalt's root buildscript block.
buildscript {
    repositories {
        mavenCentral()

        // Needed for the Android Gradle Plugin to work
        google()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:9.3.1")
        classpath("ru.vyarus:gradle-animalsniffer-plugin:2.0.1")
    }
}

plugins {
    idea
}

// Overall version number for NUI's various elements, and library versions tracked across
// subprojects - referenced from gradle/common.gradle.kts and each subproject's build.gradle.kts
// via "by rootProject.extra", since (unlike Groovy) Kotlin doesn't expose ext {} properties as
// bare identifiers to other scripts.
extra["nuiVersion"] = "3.1.1-SNAPSHOT"
extra["jomlVersion"] = "1.10.0"
extra["geomVersion"] = "0.1.0"
