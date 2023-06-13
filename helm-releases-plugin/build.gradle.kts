plugins {
    kotlin("jvm")
    id("java-gradle-plugin")
    id("com.gradle.plugin-publish")
    id("org.jetbrains.dokka")
    id("maven-publish")
}


dependencies {

    implementation(project(":helm-plugin"))

    implementation("org.unbroken-dome.gradle-plugin-utils:gradle-plugin-utils:0.5.0")
    testImplementation("org.unbroken-dome.gradle-plugin-utils:gradle-plugin-test-utils:0.5.0")
}


gradlePlugin {
    plugins {
        create("helmReleasesPlugin") {
            id = "com.citi.helm-releases"
            displayName = "Helm Releases Plugin"
            implementationClass = "com.citi.gradle.plugins.helm.release.HelmReleasesPlugin"
        }
    }
}
