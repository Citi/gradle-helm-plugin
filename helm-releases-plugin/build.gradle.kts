plugins {
    kotlin("jvm")
    id("java-gradle-plugin")
    id("com.gradle.plugin-publish")
    id("org.jetbrains.dokka")
    id("maven-publish")
    alias(libs.plugins.detekt)
    alias(libs.plugins.binaryCompatibilityValidator)
    id("kotlin-convention") // keep shared logic here
}


dependencies {

    implementation(project(":helm-plugin"))

    implementation(libs.unbrokenDomePluginUtils)
    testImplementation(libs.unbrokenDomeTestUtils)
}


gradlePlugin {
    plugins {
        create("helmReleasesPlugin") {
            id = project.extra["plugin.prefix"].toString() + ".helm-releases"
            displayName = "Helm Releases"
            implementationClass = "com.citi.gradle.plugins.helm.release.HelmReleasesPlugin"
            description = "Extension for Gradle Helm Plugin. Supports charts installation/uninstallation."
            tags.addAll("helm", "release", "install", "uninstall", "cloud", "kubernetes")
        }
    }
}
