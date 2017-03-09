package com.mrhaki.grails.plugin.xframeoptions.config

import spock.lang.Specification

import static com.mrhaki.grails.plugin.xframeoptions.web.XFrameOptionsHeaderValues.*

class XFrameOptionsConfigurationSpec extends Specification {

    //---------------------------------------------------------------------------------------------------------------------
    // determineHeaderValues
    //---------------------------------------------------------------------------------------------------------------------

    def "header value must be set to DENY if configuration property deny is true"() {
        given:
        def config = new XFrameOptionsConfiguration()
        config.deny = true

        expect:
        config.determineHeaderValue() == DENY
    }

    def "header value must be set to SAMEORIGING if configuration property sameOrigin is true"() {
        given:
        def config = new XFrameOptionsConfiguration()
        config.sameOrigin = true

        expect:
        config.determineHeaderValue() == SAME_ORIGIN
    }

    def "header value must be set to ALLOW-FROM with a value if configuration property allowFrom has a value"() {
        given:
        def config = new XFrameOptionsConfiguration()
        config.allowFrom = 'http://www.mrhaki.com'

        expect:
        config.determineHeaderValue() == "${ALLOW_FROM} http://www.mrhaki.com" as String
    }

    def "header value must be set to DENY if configuration properties are not set"() {
        given:
        def config = new XFrameOptionsConfiguration()

        expect:
        config.determineHeaderValue() == DENY

    }

    def "header value must be set to DENY if no configuration is provided"() {
        expect:
        def config = new XFrameOptionsConfiguration()
        config.determineHeaderValue() == DENY
    }
}
