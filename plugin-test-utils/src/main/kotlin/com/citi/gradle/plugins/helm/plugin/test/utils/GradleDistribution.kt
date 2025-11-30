package com.citi.gradle.plugins.helm.plugin.test.utils

sealed interface GradleDistribution {
    companion object {
        val all = buildList {
            add(Current)
            addAll(Custom.values())
        }
    }

    object Current : GradleDistribution

    /**
     * Ideally is to have couple the latest distributions from https://gradle.org/releases/.
     *
     * So we will be able to add even beta versions of Gradle in future.
     */
    enum class Custom(val version: String) : GradleDistribution {
        V9_2_0("9.2.0"),
        V9_0_0("9.0.0"),
    }
}
