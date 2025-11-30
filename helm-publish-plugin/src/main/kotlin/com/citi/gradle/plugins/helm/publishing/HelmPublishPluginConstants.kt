package com.citi.gradle.plugins.helm.publishing


/**
 * The name of the `publishing` sub-extension.
 */
internal const val HELM_PUBLISHING_EXTENSION_NAME = "publishing"


/**
 * The name of the `publishing.repositories` sub-extension.
 */
internal const val HELM_PUBLISHING_REPOSITORIES_EXTENSION_NAME = "repositories"


/**
 * The name of the publishing extension object installed on each chart.
 *
 * Note that since extension properties are accessible directly on the object, the name of the extension
 * does not really matter except for uniqueness.
 */
const val HELM_CHART_PUBLISHING_EXTENSION_NAME = "publishing"
