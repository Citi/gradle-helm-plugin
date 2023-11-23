package com.citi.gradle.plugins.helm.util

import io.kotest.matchers.equals.shouldBeEqual
import java.io.File
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir

internal class FileLockTest {
    @TempDir
    private lateinit var tempFolder: File

    private lateinit var fileToLock: File

    @BeforeEach
    fun setup() {
        tempFolder.mkdirs()
        fileToLock = File(tempFolder, "text.txt")
        fileToLock.createNewFile()
    }

    @Test
    @Disabled
    fun twoFunctionsArentAbleToUpdateFile() {
        runBlocking {
            val thread2IsReady = CompletableDeferred<Unit>()

            val taskIndices = 0..99

            val parallelTasks = taskIndices.map { taskIndex ->
                async {
                    withLockFile(fileToLock) {
                        // don't start until all tasks have been created
                        runBlocking {
                            thread2IsReady.await()
                        }

                        taskIndex
                    }
                }
            }

            thread2IsReady.complete(Unit)

            val taskResults = parallelTasks.awaitAll().toSet()

            taskResults shouldBeEqual taskIndices.toSet()
        }
    }
}