package com.conferences.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * <p>
 *     Encodes request and response
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public class EncodingFilter implements Filter {

    /**
     * <p>
     *     Encodes request to UTF-8 encoding
     *     Encodes response to UTF-8 encoding
     * </p>
     * @param servletRequest an instance representing {@link ServletRequest}
     * @param servletResponse an instance representing {@link ServletResponse}
     * @param filterChain an instance representing {@link FilterChain}
     * @throws IOException exception while may occur during filter processing
     * @throws ServletException exception while may occur during filter processing
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
