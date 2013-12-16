package com.mrhaki.grails.plugin.xframeoptions.config

import static com.mrhaki.grails.plugin.xframeoptions.web.XFrameOptionsHeaderValues.*

import org.codehaus.groovy.grails.commons.GrailsApplication

/**
 * Helper class to get header value from configuration properties and
 * to check if configuration properties are set.
 */
class ConfigUtil {

    private final GrailsApplication application

    ConfigUtil(final GrailsApplication application) {
        this.application = application
    }

    /**
     * Determine response header value based on configuration properties.
     *
     * @return Response header value.
     */
    String determineHeaderValue() {
        if (config?.deny) {
            return DENY
        }

        if (config?.sameOrigin) {
            return SAME_ORIGIN
        }

        if (config?.allowFrom) {
            return ALLOW_FROM + " ${config.allowFrom}"
        }

        return DENY
    }

    /**
     * Return URL patterns the filter needs to be applied to defined
     * in the configuration.
     *
     * @return URL patterns from the configuration, if not set default value '/*' is returned.
     */
    def getUrlPattern() {
        config?.containsKey('urlPattern') ? config.urlPattern : '/*'
    }

    /**
     * The servlet filter needs to enabled if the enabled configuration property is true or
     * no configuration is set.
     *
     * @return True if enabled property is true or no configuration is defined.
     */
    boolean isFilterEnabled() {
        !config || config.enabled || (hasCustomSettings() && !config.enabled)
    }

    private boolean hasCustomSettings() {
        config.containsKey('deny') || config.containsKey('sameOrigin') || config.containsKey('allowFrom')
    }

    private getConfig() {
        final ConfigObject config = application.config.grails.plugin.xframeoptions
        config
    }

}
