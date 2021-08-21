package com.conferences.filter;

import javax.servlet.*;
import java.io.IOException;

public class LangFilter extends DispatcherFilter {
    @Override
    public void processApp(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
