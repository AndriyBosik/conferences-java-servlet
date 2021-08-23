package com.conferences.filter;

import com.conferences.filter.model.ServletData;
import com.conferences.filter.model.UrlData;

import javax.servlet.*;
import java.io.IOException;

public class UrlModifierFilter extends UrlDividerFilter {

    @Override
    protected void handleFilter(ServletData servletData, UrlData urlData, FilterChain filterChain) throws ServletException, IOException {
        servletData.getServletRequest()
            .getRequestDispatcher("/app" + urlData.getPath())
            .forward(
                servletData.getServletRequest(),
                servletData.getServletResponse()
            );
    }
}
