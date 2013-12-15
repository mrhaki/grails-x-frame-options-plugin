package com.mrhaki.grails.plugin.xframeoptions.web

import static com.mrhaki.grails.plugin.xframeoptions.web.XFrameOptionsHeaderValues.DENY

import org.springframework.mock.web.MockFilterChain
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import spock.lang.Specification

class XFrameOptionsFilterSpec extends Specification {

    private final MockHttpServletRequest request = new MockHttpServletRequest()
    private final MockHttpServletResponse response = new MockHttpServletResponse()
    private final MockFilterChain chain = new MockFilterChain()

    def "set header value based on value passed via constructor"() {
        given:
        final XFrameOptionsFilter filter = new XFrameOptionsFilter(DENY)

        when:
        filter.doFilterInternal(request, response, chain)

        then:
        response.getHeader('X-FRAME-OPTIONS') == DENY
    }

    def "set header value to DENY with default constructor"() {
        given:
        final XFrameOptionsFilter filter = new XFrameOptionsFilter()

        when:
        filter.doFilterInternal(request, response, chain)

        then:
        response.getHeader('X-FRAME-OPTIONS') == DENY
    }

}
