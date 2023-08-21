package org.unbrokendome.gradle.plugins.helm.dsl.dependencies

import org.gradle.api.artifacts.Configuration
import org.unbrokendome.gradle.pluginutils.capitalize


/**
 * Gets the name of the [Configuration] that contains the chart dependencies for the given chart.
 *
 * @param name the chart name
 * @return the name of the chart's dependencies configuration
 */
internal fun chartDependenciesConfigurationName(name: String) =
    "helm${name.capitalize()}Dependencies"
