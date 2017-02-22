package com.mrhaki.grails.plugin.xframeoptions

import com.mrhaki.grails.plugin.xframeoptions.config.XFrameOptionsConfiguration
import com.mrhaki.grails.plugin.xframeoptions.web.XFrameOptionsFilter
import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import grails.plugins.Plugin
import groovy.util.logging.Slf4j
import org.springframework.boot.web.servlet.FilterRegistrationBean

@Slf4j
class XFrameOptionsGrailsPlugin extends Plugin implements GrailsConfigurationAware {

    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "3.2.6 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    def title = 'X-Frame-Options Plugin'
    def author = 'Hubert A. Klein Ikkink aka mrhaki'
    def authorEmail = "h.kleinikkink@gmail.com"
    def description = 'Servlet filter that adds a X-FRAME-OPTIONS response header.'

    def profiles = ['web']

    def documentation = "http://github.com/mrhaki/grails-x-frame-options-plugin/"

    def license = 'APACHE'

    def issueManagement = [system: 'GitHub', url: 'https://github.com/mrhaki/grails-x-frame-options-plugin/issues']

    def scm = [url: "https://github.com/mrhaki/grails-x-frame-options-plugin/"]

    Closure doWithSpring() { {->
            def application = grailsApplication
            def config = application.config
            def xFrameOptionsConfiguration = new XFrameOptionsConfiguration(config)
            if (xFrameOptionsConfiguration.filterEnabled) {
                final String value = xFrameOptionsConfiguration.determineHeaderValue()
                log.debug "Setting X-Frame-Options header to $value"
                xFrameOptionsFilter(FilterRegistrationBean) {
                    filter = bean(XFrameOptionsFilter, value)
                    urlPatterns = xFrameOptionsConfiguration.urlPattern.tokenize(',')
                    order = FilterRegistrationBean.LOWEST_PRECEDENCE
                }
            }
        }
    }

    void doWithDynamicMethods() {
        // TODO Implement registering dynamic methods to classes (optional)
    }

    void doWithApplicationContext() {
        // TODO Implement post initialization spring config (optional)
    }

    void onChange(Map<String, Object> event) {
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    void onConfigChange(Map<String, Object> event) {
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    void onShutdown(Map<String, Object> event) {
        // TODO Implement code that is executed when the application shuts down (optional)
    }

    @Override
    void setConfiguration(Config co) {


    }
}
