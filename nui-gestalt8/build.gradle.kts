plugins {
    `java-library`
    `maven-publish`
}

apply(from = "$rootDir/gradle/common.gradle")

repositories {
    google()
}

dependencies {
    api(group = "org.terasology.gestalt", name = "gestalt-module", version = "8.0.2-SNAPSHOT")
    api(group = "org.terasology.gestalt", name = "gestalt-asset-core", version = "8.0.2-SNAPSHOT")
    // For org.terasology.context.annotation.API - moved here from gestalt-module's own
    // sandbox package between gestalt 7 and 8.
    api(group = "org.terasology.gestalt", name = "gestalt-inject", version = "8.0.2-SNAPSHOT")

    implementation("org.slf4j:slf4j-api:1.7.30")
    // Unlike nui-gestalt5/nui-gestalt7, no dependency on org.reflections (or its
    // org.terasology fork) at all: gestalt 8 replaced runtime Reflections scanning with a
    // compile-time-generated ClassIndex, aggregated by ModuleEnvironment itself - see
    // ModuleTypeRegistry.
    implementation(group = "com.google.code.gson", name = "gson", version = "2.6.2")
    implementation(group = "com.google.guava", name = "guava", version = "23.0")

    api(project(":nui"))
    api(project(":nui-reflect"))
}
