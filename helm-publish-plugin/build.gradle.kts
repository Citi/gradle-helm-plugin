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

val functionalTest by sourceSets.creating

dependencies {

    implementation(project(":helm-plugin"))

    implementation(libs.okHttp) {
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib")
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib-common")
    }
    implementation(libs.okHttpTls) {
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib")
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib-common")
    }

    implementation(libs.unbrokenDomePluginUtils)

    testImplementation(libs.unbrokenDomeTestUtils)

    "functionalTestImplementation"(project(":plugin-test-utils"))
    "functionalTestImplementation"(libs.okHttpMockWebServer)
    "functionalTestRuntimeOnly"(libs.junitPlatform)
}


gradlePlugin {
    testSourceSets(functionalTest)
    plugins {
        create("helmPublishPlugin") {
            id = project.extra["plugin.prefix"].toString() + ".helm-publish"
            displayName = "Helm Publish"
            implementationClass = "com.citi.gradle.plugins.helm.publishing.HelmPublishPlugin"
            description = "Extension for Gradle Helm Plugin. Allows helm chart publishing. Helm doesn't have this feature, so different publications are used for different helm repository providers"
            tags.addAll("helm", "publish")
        }
    }
}

apiValidation {
    ignoredPackages.add("com.citi.gradle.plugins.helm.publishing.dsl.internal")
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
