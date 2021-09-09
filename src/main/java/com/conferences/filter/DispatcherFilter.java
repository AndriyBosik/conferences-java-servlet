package com.conferences.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import com.conferences.controller.FrontController;

/**
 * <p>
 *
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public abstract class DispatcherFilter implements Filter {

    /**
     * <p>
     *     Dispatches requests to {@link FrontController} or to resources location
     * </p>
     * @param servletRequest an instance of {@link ServletRequest} class
     * @param servletResponse an instance of {@link ServletResponse} class
     * @param filterChain an instance of {@link FilterChain} class
     * @throws IOException exception while may occur during filter processing
     * @throws ServletException exception while may occur during filter processing
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String path = request.getRequestURI().substring(request.getContextPath().length());

        path = path.toLowerCase();

        if (path.startsWith("/resources/")) {
            processResources(servletRequest, servletResponse, filterChain);
        } else {
            processApp(servletRequest, servletResponse, filterChain);
        }
    }
    protected void processResources(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public abstract void processApp(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException;
}
