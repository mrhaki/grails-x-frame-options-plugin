package com.mrhaki.grails.plugin.xframeoptions

import com.mrhaki.grails.plugin.xframeoptions.config.XFrameOptionsConfiguration
import com.mrhaki.grails.plugin.xframeoptions.web.XFrameOptionsFilter
import grails.plugins.Plugin
import groovy.util.logging.Slf4j
import org.springframework.boot.web.servlet.FilterRegistrationBean

@Slf4j
class XFrameOptionsGrailsPlugin extends Plugin {

    def grailsVersion = "3.2.6 > *"

    def title = 'X-Frame-Options Plugin'
    def author = 'Hubert A. Klein Ikkink aka mrhaki'
    def authorEmail = "h.kleinikkink@gmail.com"
    def developers = [
            [name: "Sergio del Amo Caballero", email: "delamos@objectcomputing.com"],
            [name: "Søren Berg Glasius", email: "soeren@glasius.dk"],
    ]
    def description = 'Servlet filter that adds a X-FRAME-OPTIONS response header.'

    def profiles = ['web']

    def documentation = "http://github.com/mrhaki/grails-x-frame-options-plugin/"

    def license = 'APACHE'

    def issueManagement = [system: 'GitHub', url: 'https://github.com/mrhaki/grails-x-frame-options-plugin/issues']

    def scm = [url: "https://github.com/mrhaki/grails-x-frame-options-plugin/"]

    Closure doWithSpring() {
        { ->
            def xFrameOptionsConfiguration = new XFrameOptionsConfiguration(config)
            if (xFrameOptionsConfiguration.filterEnabled) {
                final String value = xFrameOptionsConfiguration.determineHeaderValue()
                log.debug "Setting X-Frame-Options header to $value"
                xFrameOptionsFilter(FilterRegistrationBean) {
                    filter = bean(XFrameOptionsFilter, value)
                    urlPatterns = xFrameOptionsConfiguration.urlPatterns
                    order = FilterRegistrationBean.LOWEST_PRECEDENCE
                }
            }
        }
    }
}
