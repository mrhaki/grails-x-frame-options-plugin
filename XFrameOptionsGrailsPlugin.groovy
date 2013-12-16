import com.mrhaki.grails.plugin.xframeoptions.config.ConfigUtil
import org.springframework.web.filter.DelegatingFilterProxy

class XframeoptionsGrailsPlugin {

    def version = '1.0'
    def grailsVersion = '1.2 > *'
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    def title = 'X-Frame-Options Plugin'
    def author = 'Hubert A. Klein Ikkink aka mrhaki'
    def authorEmail = "h.kleinikkink@gmail.com"
    def description = 'Servlet filter that adds a X-FRAME-OPTIONS response header.'
    def documentation = "http://github.com/mrhaki/grails-x-frame-options-plugin/"

    def license = 'APACHE'
    def scm = [url: "https://github.com/mrhaki/grails-x-frame-options-plugin/"]
    def issueManagement = [system: 'GitHub', url: 'https://github.com/mrhaki/grails-x-frame-options-plugin/issues']

    def getWebXmlFilterOrder() {
        def FilterManager = getClass().getClassLoader().loadClass('grails.plugin.webxml.FilterManager')
        [XFrameOptionsFilter: FilterManager.CHAR_ENCODING_POSITION + 1]
    }

    def doWithWebDescriptor = { xml ->
        final ConfigUtil configUtil = new ConfigUtil(application)
        if (configUtil.filterEnabled) {
            // we add the filter(s) right after the last context-param
            def contextParam = xml.'context-param'

            // the name of the filter matches the name of the Spring bean that it delegates to
            contextParam[-1] + {
                'filter' {
                    'filter-name'('XFrameOptionsFilter')
                    'filter-class'(DelegatingFilterProxy.name)
                }
            }

            final urlPattern = configUtil.urlPattern
            final List<String> urlPatterns = urlPattern instanceof List ? urlPattern : [urlPattern]

            def mappingLocation = xml.'filter-mapping'
            urlPatterns.each { pattern ->
                mappingLocation[-1] + {
                    'filter-mapping' {
                        'filter-name'('XFrameOptionsFilter')
                        'url-pattern'(pattern)
                    }
                }
            }
        }
    }

    def doWithSpring = {
        final ConfigUtil configUtil = new ConfigUtil(application)
        if (configUtil.filterEnabled) {
            final String headerValue = configUtil.determineHeaderValue()
            if (log.debugEnabled) {
                log.debug "Setting X-Frame-Options header to $headerValue"
            }

            XFrameOptionsFilter(com.mrhaki.grails.plugin.xframeoptions.web.XFrameOptionsFilter, headerValue)
        }
    }

}
