package com.citi.gradle.plugins.helm.publishing.dsl

import org.gradle.api.model.ObjectFactory
import org.gradle.api.plugins.ExtensionAware
import org.gradle.internal.reflect.Instantiator
import com.citi.gradle.plugins.helm.publishing.HELM_PUBLISHING_REPOSITORIES_EXTENSION_NAME
import org.gradle.api.provider.Property
import org.unbrokendome.gradle.pluginutils.property
import javax.inject.Inject


/**
 * Configures the publishing of Helm charts to remote repositories.
 */
interface HelmPublishingExtension {
    val publish: Property<Boolean>
}


private open class DefaultHelmPublishingExtension
@Inject constructor(objectFactory: ObjectFactory) : HelmPublishingExtension {
    override val publish: Property<Boolean> =
        objectFactory.property<Boolean>().convention(true)
}


/**
 * Creates a new [HelmPublishingExtension].
 *
 * @receiver the Gradle [ObjectFactory]
 * @return the created [HelmPublishingExtension] object
 */
internal fun ObjectFactory.createHelmPublishingExtension(instantiator: Instantiator): HelmPublishingExtension =
    newInstance(DefaultHelmPublishingExtension::class.java)
        .apply {
            (this as ExtensionAware).extensions
                .add(
                    HelmPublishingRepositoryContainer::class.java,
                    HELM_PUBLISHING_REPOSITORIES_EXTENSION_NAME,
                    newHelmPublishingRepositoryContainer(instantiator)
                )
        }
