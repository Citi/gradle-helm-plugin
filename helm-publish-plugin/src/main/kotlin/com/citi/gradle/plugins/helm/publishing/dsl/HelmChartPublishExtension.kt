package com.citi.gradle.plugins.helm.publishing.dsl

import com.citi.gradle.plugins.helm.dsl.HelmChart
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.unbrokendome.gradle.pluginutils.property
import org.unbrokendome.gradle.pluginutils.requiredExtension
import javax.inject.Inject


/**
 * Extension that adds publishing-related properties to each chart.
 */
interface HelmChartPublishExtension {
    /**
     * Indicates whether tasks for publishing this chart should be created automatically.
     *
     * Defaults to `true`.
     */
    val publish: Property<Boolean>
}


private open class DefaultHelmChartPublishExtension
@Inject constructor(objectFactory: ObjectFactory) : HelmChartPublishExtension {

    override val publish: Property<Boolean> =
        objectFactory.property<Boolean>()
            .convention(true)
}


/**
 * Creates a new [HelmChartPublishExtension] using the given [ObjectFactory].
 *
 * @receiver the [ObjectFactory] used to instantiate the convention object
 * @return the [HelmChartPublishExtension]
 */
internal fun ObjectFactory.createHelmChartPublishExtension(): HelmChartPublishExtension =
    newInstance(DefaultHelmChartPublishExtension::class.java)

/**
 * Gets the [HelmChartPublishExtension] object for the given chart.
 */
internal val HelmChart.publishExtension: HelmChartPublishExtension
    get() = requiredExtension()
