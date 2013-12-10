class XFrameOptionsPluginGrailsPlugin {
    def version = "0.1"
    def grailsVersion = "1.0 > *"
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    def title = "X-Frame-Options Plugin"
    def author = "Hubert A. Klein Ikkink aka mrhaki"
    def authorEmail = "h.kleinikkink@gmail.com"
    def description = '''\
Servlet filter that adds a X-FRAME-OPTIONS response header.
'''
    def documentation = "http://github.com/mrhaki/grails-x-frame-options-plugin/"

    def license = "APACHE"
    def scm = [ url: "https://github.com/mrhaki/grails-x-frame-options-plugin/" ]

    // Extra (optional) plugin metadata

    // Details of company behind the plugin (if there is one)
//    def organization = [ name: "My Company", url: "http://www.my-company.com/" ]

    // Any additional developers beyond the author specified above.
//    def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]

    // Location of the plugin's issue tracker.
//    def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPMYPLUGIN" ]


    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before
    }

    def doWithSpring = {
        def conf = application.config.grails.plugins.xframeoptions
        if (conf.enabled) {
            if (conf.mode) {
                XFrameOptionsFilter(com.mrhaki.grails.plugins.xframeoptions.web.XFrameOptionsFilter) {
                    mode = conf.mode
                }
            } else {
                XFrameOptionsFilter(com.mrhaki.grails.plugins.xframeoptions.web.XFrameOptionsFilter)
            }
        }
    }

    def doWithApplicationContext = { ctx ->
        // TODO Implement post initialization spring config (optional)
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    def onShutdown = { event ->
        // TODO Implement code that is executed when the application shuts down (optional)
    }
}
