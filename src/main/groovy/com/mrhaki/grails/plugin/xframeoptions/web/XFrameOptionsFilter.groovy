package com.mrhaki.grails.plugin.xframeoptions.web

import groovy.transform.CompileStatic
import org.springframework.web.filter.OncePerRequestFilter

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import static com.mrhaki.grails.plugin.xframeoptions.web.XFrameOptionsHeaderValues.DENY

/**
 * <p>
 *     Servlet filter that adds the response header X-FRAME-OPTIONS.
 *     The header value to be used is passed via the constructor.
 * </p>
 * <p>
 *     More information available at
 *     <a href="https://www.owasp.org/index.php/Clickjacking_Defense_Cheat_Sheet#Defending_with_X-Frame-Options_Response_Headers">OWASP</a>.
 * </p>
 *
 */
@CompileStatic
class XFrameOptionsFilter extends OncePerRequestFilter {

    /**
     * Response header name.
     */
    private static final String HEADER_NAME = "X-Frame-Options"

    /**
     * Default value to be used when nothing is defined in the configuration.
     */
    private static final String DEFAULT_MODE = DENY

    /**
     * Store mode which is the header value to be used.
     */
    String headerValue

    /**
     * Set defualt header value {@link }
     */
    XFrameOptionsFilter() {
        this(DEFAULT_MODE)
    }

    /**
     * Set header value to be used for the HTTP response header.
     *
     * @param headerValue Value for the header.
     */
    XFrameOptionsFilter(final String headerValue) {
        this.headerValue = headerValue
    }

    /**
     * Set response header value and continue the filter processing.
     *
     * @param request Request is not changed.
     * @param response Response with extra header.
     * @param filterChain Filter chain to be processed.
     * @throws ServletException Exception defined by interface.
     * @throws IOException Exception defined by interface.
     */
    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        response.addHeader(HEADER_NAME, headerValue)
        filterChain.doFilter(request, response)
    }

}