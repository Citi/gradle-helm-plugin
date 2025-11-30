plugins {
    kotlin("jvm")
    alias(libs.plugins.detekt)
    id("kotlin-convention") // keep shared logic here
}

dependencies {
    api(gradleTestKit())
    api(libs.bundles.defaultTests)
    runtimeOnly(libs.junitEngine)
}