package com.citi.gradle.plugins.helm.tests.functional

import com.citi.gradle.plugins.helm.plugin.test.utils.DefaultGradleRunnerParameters
import com.citi.gradle.plugins.helm.plugin.test.utils.GradleRunnerProvider
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldNotContain
import java.io.File
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.io.TempDir
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

internal class SuppressEnvironmentLoggingTest {

    private val sourceDirectory = File("./src/functionalTest/resources/test/suppress-environment-logging")

    @TempDir
    private lateinit var testProjectDir: File

    @BeforeEach
    fun setup() {
        sourceDirectory.copyRecursively(target = testProjectDir)
    }

    @ParameterizedTest
    @MethodSource("com.citi.gradle.plugins.helm.plugin.test.utils.DefaultGradleRunnerParameters#getDefaultParameterSet")
    fun doNotSuppressEnvironmentLoggingByDefault(parameters: DefaultGradleRunnerParameters) {
        // given

        selectBuildGradle("without-suppress")

        val helmExecutableParameter =
            HelmExecutable.getExecutableParameterForHelm(testProjectDir)

        val arguments = listOf(
            "helmPackage", 
            "--info",
            helmExecutableParameter.parameterValue
        )

        val gradleRunner = GradleRunnerProvider
            .createRunner(parameters)
            .withProjectDir(testProjectDir)
            .withArguments(arguments)

        // when
        val result = gradleRunner.build()

        // then
        val output = result.output

        output shouldContain "BUILD SUCCESSFUL"
        output shouldContain "with environment:"
        output shouldNotContain "(environment logging is suppressed)"
    }

    @ParameterizedTest
    @MethodSource("com.citi.gradle.plugins.helm.plugin.test.utils.DefaultGradleRunnerParameters#getDefaultParameterSet")
    fun suppressEnvironmentLoggingWithCommandLineProperty(parameters: DefaultGradleRunnerParameters) {
        // given

        selectBuildGradle("without-suppress")

        val helmExecutableParameter =
            HelmExecutable.getExecutableParameterForHelm(testProjectDir)

        val arguments = listOf(
            "helmPackage", 
            "--info",
            "-Phelm.suppressEnvironmentLogging=true",
            helmExecutableParameter.parameterValue
        )

        val gradleRunner = GradleRunnerProvider
            .createRunner(parameters)
            .withProjectDir(testProjectDir)
            .withArguments(arguments)

        // when
        val result = gradleRunner.build()

        // then
        val output = result.output

        output shouldContain "BUILD SUCCESSFUL"
        output shouldContain "(environment logging is suppressed)"
        output shouldNotContain "with environment:"
    }

    @ParameterizedTest
    @MethodSource("com.citi.gradle.plugins.helm.plugin.test.utils.DefaultGradleRunnerParameters#getDefaultParameterSet")
    fun doNotSuppressEnvironmentLoggingWithCommandLineProperty(parameters: DefaultGradleRunnerParameters) {
        // given

        selectBuildGradle("without-suppress")

        val helmExecutableParameter =
            HelmExecutable.getExecutableParameterForHelm(testProjectDir)

        val arguments = listOf(
            "helmPackage", 
            "--info",
            "-Phelm.suppressEnvironmentLogging=false",
            helmExecutableParameter.parameterValue
        )

        val gradleRunner = GradleRunnerProvider
            .createRunner(parameters)
            .withProjectDir(testProjectDir)
            .withArguments(arguments)

        // when
        val result = gradleRunner.build()

        // then
        val output = result.output

        output shouldContain "BUILD SUCCESSFUL"
        output shouldContain "with environment:"
        output shouldNotContain "(environment logging is suppressed)"
    }

    @ParameterizedTest
    @MethodSource("com.citi.gradle.plugins.helm.plugin.test.utils.DefaultGradleRunnerParameters#getDefaultParameterSet")
    fun suppressEnvironmentLoggingWithDsl(parameters: DefaultGradleRunnerParameters) {
        // given

        selectBuildGradle("with-suppress")

        val helmExecutableParameter =
            HelmExecutable.getExecutableParameterForHelm(testProjectDir)

        val arguments = listOf(
            "helmPackage", 
            "--info",
            helmExecutableParameter.parameterValue
        )

        val gradleRunner = GradleRunnerProvider
            .createRunner(parameters)
            .withProjectDir(testProjectDir)
            .withArguments(arguments)

        // when
        val result = gradleRunner.build()

        // then
        val output = result.output

        output shouldContain "BUILD SUCCESSFUL"
        output shouldContain "(environment logging is suppressed)"
        output shouldNotContain "with environment:"
    }

    private fun selectBuildGradle(selection: String) {
        File(testProjectDir, "build.gradle.$selection")
            .copyTo(File(testProjectDir, "build.gradle"))
    }
}
