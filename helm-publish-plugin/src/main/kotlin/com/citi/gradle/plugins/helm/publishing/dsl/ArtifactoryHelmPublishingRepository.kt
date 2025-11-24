package com.citi.gradle.plugins.helm.publishing.dsl

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import com.citi.gradle.plugins.helm.dsl.credentials.internal.SerializableCredentials
import com.citi.gradle.plugins.helm.dsl.credentials.internal.toSerializable
import com.citi.gradle.plugins.helm.publishing.publishers.AbstractHttpHelmChartPublisher
import com.citi.gradle.plugins.helm.publishing.publishers.HelmChartPublisher
import com.citi.gradle.plugins.helm.publishing.publishers.PublisherParams
import com.citi.gradle.plugins.helm.util.calculateDigestHex
import org.unbrokendome.gradle.pluginutils.property
import java.io.File
import java.net.URI
import javax.inject.Inject


interface ArtifactoryHelmPublishingRepository : HelmPublishingRepository {

    /**
     * The path, relative to the base [url], where the chart packages will be uploaded.
     *
     * May contain the following placeholders:
     *
     * - `{name}` will be replaced with the chart name
     * - `{version}` will be replaced with the chart version
     * - `{filename}` will be replaced with the filename of the packaged chart, i.e. `{name}-{version}.tgz`
     *
     * Defaults to `/{name}-{version}.tgz`.
     */
    val uploadPath: Property<String>
}


private open class DefaultArtifactoryHelmPublishingRepository
@Inject constructor(
    name: String,
    objects: ObjectFactory
) : AbstractHelmPublishingRepository(objects, name), ArtifactoryHelmPublishingRepository {

    override val publisherParams: PublisherParams
        get() = ArtifactoryPublisherParams(
            url = url.get(),
            credentials = configuredCredentials.orNull?.toSerializable(),
            uploadPath = uploadPath.get()
        )

    override val uploadPath: Property<String> =
        objects.property<String>()
            .convention("/{name}-{version}.tgz")

    private class ArtifactoryPublisherParams(
        private val url: URI,
        private val credentials: SerializableCredentials?,
        private val uploadPath: String
    ) : PublisherParams {

        override fun createPublisher(): HelmChartPublisher =
            ArtifactoryPublisher(url, credentials, uploadPath)
    }


    private class ArtifactoryPublisher(
        url: URI,
        credentials: SerializableCredentials?,
        private val uploadPath: String
    ) : AbstractHttpHelmChartPublisher(url, credentials) {

        override val uploadMethod: String
            get() = "PUT"


        override fun uploadPath(chartName: String, chartVersion: String): String =
            this.uploadPath
                .replace("{name}", chartName)
                .replace("{version}", chartVersion)
                .replace("{filename}", "$chartName-$chartVersion.tgz")


        override fun additionalHeaders(chartName: String, chartVersion: String, chartFile: File): Map<String, String> =
            mapOf(
                "X-Checksum-Sha1" to chartFile.calculateDigestHex("SHA-1"),
                "X-Checksum-Sha256" to chartFile.calculateDigestHex("SHA-256"),
                "X-Checksum-Md5" to chartFile.calculateDigestHex("MD5")
            )
    }
}


internal fun ObjectFactory.newArtifactoryHelmPublishingRepository(name: String): ArtifactoryHelmPublishingRepository =
    newInstance(DefaultArtifactoryHelmPublishingRepository::class.java, name)
