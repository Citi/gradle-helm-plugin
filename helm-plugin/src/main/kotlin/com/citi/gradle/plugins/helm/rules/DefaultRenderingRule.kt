package com.citi.gradle.plugins.helm.rules

import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.internal.plugins.AbstractRule
import com.citi.gradle.plugins.helm.dsl.HelmRendering


/**
 * A rule that will create a [HelmRendering] named "default".
 */
internal class DefaultRenderingRule(
    private val renderings: NamedDomainObjectContainer<HelmRendering>
) : AbstractRule() {

    override fun getDescription(): String {
        return "default rendering"
    }

    override fun apply(domainObjectName: String) {
        if (domainObjectName == HelmRendering.DEFAULT_RENDERING_NAME) {
            renderings.create(HelmRendering.DEFAULT_RENDERING_NAME)
        }
    }
}
