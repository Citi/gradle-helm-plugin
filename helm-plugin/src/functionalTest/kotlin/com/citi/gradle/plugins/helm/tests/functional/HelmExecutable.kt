package com.citi.gradle.plugins.helm.tests.functional

import java.io.File

/**
 * Helping class creating helm executable that does nothing
 *
 * Is needed to simulate real helm (we don't need to verify this) and ignore actions done by other parts of a plugin.
 */

internal object HelmExecutable {
    private val helmExecutablesDirectory = File("./src/functionalTest/resources/executable")
    private val fileExtension = let {
        val isWindows = System.getProperty("os.name").contains("Windows", ignoreCase = true)

        if (isWindows) {
            "bat"
        } else {
            "sh"
        }
    }

    private val executableDummyHelm = helmExecutablesDirectory.resolve("helm.$fileExtension")

    /**
     * 2. Creates gradle parameter with path to that executable
     */
    fun getExecutableParameterForHelm(
        temporaryFolder: File
    ): HelmExecutableParameter {
        
        val destinationExecutableFile = temporaryFolder.resolve("helm.$fileExtension")
        
        executableDummyHelm.copyTo(destinationExecutableFile)
        destinationExecutableFile.setExecutable(true)

        return HelmExecutableParameter(destinationExecutableFile)
    }

    private fun getAbsoluteNormalizedPath(file: File): String {
      return file
        .absoluteFile
        .normalize()
        .path
        .replace('\\', '/')
    }

    internal class HelmExecutableParameter(
        path: File
    ) {
        val parameterValue = let {
            val normalizedFilePath = getAbsoluteNormalizedPath(path)

            "-Phelm.executable=${normalizedFilePath}"
        }
    }
}