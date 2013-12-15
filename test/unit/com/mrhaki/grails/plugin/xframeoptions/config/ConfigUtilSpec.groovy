package com.mrhaki.grails.plugin.xframeoptions.config

import static com.mrhaki.grails.plugin.xframeoptions.web.XFrameOptionsHeaderValues.*

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

@TestMixin(GrailsUnitTestMixin)
class ConfigUtilSpec extends Specification {

    //---------------------------------------------------------------------------------------------------------------------
    // determineHeaderValues
    //---------------------------------------------------------------------------------------------------------------------

    def "header value must be set to DENY if configuration property deny is true"() {
        given:
        setConfigurationValue 'deny', true

        expect:
        createConfigUtil().determineHeaderValue() == DENY
    }

    def "header value must be set to SAMEORIGING if configuration property sameOrigin is true"() {
        given:
        setConfigurationValue 'sameOrigin', true

        expect:
        createConfigUtil().determineHeaderValue() == SAME_ORIGIN
    }

    def "header value must be set to ALLOW-FROM with a value if configuration property allowFrom has a value"() {
        given:
        setConfigurationValue 'allowFrom', 'http://www.mrhaki.com'

        expect:
        createConfigUtil().determineHeaderValue() == "${ALLOW_FROM} http://www.mrhaki.com"
    }

    def "header value must be set to DENY if configuration properties are not set"() {
        given:
        setConfigurationValue null, null

        expect:
        createConfigUtil().determineHeaderValue() == DENY

    }

    def "header value must be set to DENY if no configuration is provided"() {
        expect:
        createConfigUtil().determineHeaderValue() == DENY
    }

    private void setConfigurationValue(key, value) {
        config.grails.plugin.xframeoptions = key ? [(key): value] : [:]
    }

    private createConfigUtil() {
        new ConfigUtil(grailsApplication)
    }
}
