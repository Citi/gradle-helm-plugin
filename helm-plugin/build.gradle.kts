plugins {
    kotlin("jvm")
    id("java-gradle-plugin")
    id("com.gradle.plugin-publish")
    id("org.jetbrains.dokka")
    id("maven-publish")
    alias(libs.plugins.detekt)
    alias(libs.plugins.binaryCompatibilityValidator)
}

val functionalTest by sourceSets.creating

dependencies {

    implementation(libs.snakeyaml)
    implementation(libs.orgJson)

    implementation(libs.unbrokenDomePluginUtils)

    testImplementation(libs.jsonPath)
    testImplementation(libs.jacksonDataBind)
    testImplementation(libs.jacksonDataFormatYaml)

    testImplementation(libs.okHttpMockWebServer)

    testImplementation(libs.coroutinesCore)
    testImplementation(libs.unbrokenDomeTestUtils)
    testImplementation(libs.bundles.defaultTests)
    testRuntimeOnly(libs.junitEngine)

    "functionalTestImplementation"(project(":plugin-test-utils"))
}


gradlePlugin {
    testSourceSets(functionalTest)
    plugins {
        create("helmCommandsPlugin") {
            id = "com.citi.helm-commands"
            displayName = "Helm Commands"
            implementationClass = "com.citi.gradle.plugins.helm.command.HelmCommandsPlugin"
            description = "Wrapper for common helm commands"
            tags.addAll("helm", "helm commands", "kubernetes", "k8s", "cloud")
        }
        create("helmPlugin") {
            id = "com.citi.helm"
            displayName = "Helm"
            implementationClass = "com.citi.gradle.plugins.helm.HelmPlugin"
            description = "Gradle plugin to help preparing Helm Charts. Supports charts packaging, linting, dependencies update, etc."
            tags.addAll("helm", "package", "kubernetes", "k8s", "cloud", "repository", "lint")
        }
    }
}

apiValidation {
    ignoredPackages.add("com.citi.gradle.plugins.helm.dsl.internal")
}

val functionalTestTask = tasks.register<Test>("functionalTest") {
    description = "Runs the integration tests."
    group = "verification"
    testClassesDirs = functionalTest.output.classesDirs
    classpath = functionalTest.runtimeClasspath
    mustRunAfter(tasks.test)

    val urlOverrideProperty = "com.citi.gradle.helm.plugin.distribution.url.prefix"
    findProperty(urlOverrideProperty)?.let { urlOverride ->
        systemProperty(urlOverrideProperty, urlOverride)
    }
}

tasks.build {
    dependsOn(functionalTestTask)
}
