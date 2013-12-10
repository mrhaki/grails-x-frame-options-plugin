package com.mrhaki.grails.plugins.xframeoptions.web;

import java.io.IOException;
import java.lang.Override;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * <p>
 *     Servlet filter that adds the response header X-FRAME-OPTIONS.
 * </p>
 * <p>
 *     More information available at
 *     <a href="https://www.owasp.org/index.php/Clickjacking_Defense_Cheat_Sheet#Defending_with_X-Frame-Options_Response_Headers">OWASP</a>.
 * </p>
 *
 */
public class XFrameOptionsFilter extends OncePerRequestFilter {

    /**
     * Response header name.
     */
    private static final String HEADER_NAME = "X-FRAME-OPTIONS";

    /**
     * Default value to be used when nothing is defined in the configuration.
     */
    private static final String DEFAULT_MODE = "DENY";

    /**
     * Store mode which is the header value to be used.
     */
    private String mode = DEFAULT_MODE;

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
        response.addHeader(HEADER_NAME, headerValue());
        filterChain.doFilter(request, response);
    }

    private String headerValue() {
        return mode.toUpperCase();
    }

    public void setMode(final String mode) {
        this.mode = mode;
    }
}