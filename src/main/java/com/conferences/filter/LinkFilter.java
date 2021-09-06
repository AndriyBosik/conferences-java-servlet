package com.conferences.filter;

import com.conferences.config.Defaults;
import com.conferences.model.ServletData;
import com.conferences.model.UrlData;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class LinkFilter extends UrlDividerFilter {

    @Override
    protected void handleFilter(ServletData servletData, UrlData urlData, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletData.getServletRequest();

        request.setAttribute(Defaults.CURRENT_LANG.toString(), urlData.getLang());
        request.setAttribute(Defaults.CURRENT_LINK.toString(), urlData.getPath());

        filterChain.doFilter(servletData.getServletRequest(), servletData.getServletResponse());
    }
}
