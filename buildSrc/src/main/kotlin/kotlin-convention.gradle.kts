plugins {
    `java-library`
}
/**
Embedded plugin has common logic for different projects. So, mainly it is here to have an ability to put more code into Gradle files.
 */

// we will lock dependencies on these two configurations. We aren't interested in others, however these ones will be used
// for local invocation
val configurationsToLock: List<Configuration> = listOf("runtimeClasspath", "testRuntimeClasspath").map { configurations[it] }

// lock selected configurations. The build is failed if they aren't updated
configurationsToLock.forEach {
    it.resolutionStrategy.activateDependencyLocking()
}

// copied from https://docs.gradle.org/current/userguide/dependency_locking.html#sec:lock-all-configurations-in-one-build-execution
// however we resolve only two configurations we are interested in
// So, if there are any issues with `gradle.lockfile`, call `./gradlew resolveAndLockAll --write-locks`
tasks.register("resolveAndLockAll") {
    notCompatibleWithConfigurationCache("Filters configurations at execution time")
    doFirst {
        require(gradle.startParameter.isWriteDependencyLocks) { "$path must be run from the command line with the `--write-locks` flag" }
    }
    doLast {
        configurationsToLock.forEach {
            // resolve the configuration - it means that Gradle will generate lockfile for them
            it.resolve()
        }
    }
}