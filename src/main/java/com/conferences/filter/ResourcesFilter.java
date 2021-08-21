package com.conferences.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class ResourcesFilter extends DispatcherFilter {

    @Override
    public void processApp(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String path = request.getRequestURI().substring(request.getContextPath().length());

        servletRequest.getRequestDispatcher("/app" + path).forward(servletRequest, servletResponse);
    }

}
