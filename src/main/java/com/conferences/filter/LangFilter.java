package com.conferences.filter;

import com.conferences.filter.model.ServletData;
import com.conferences.filter.model.UrlData;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class LangFilter extends UrlDividerFilter {

    @Override
    protected void handleFilter(ServletData servletData, UrlData urlData, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletData.getServletRequest();
        request.getSession().setAttribute("lang", urlData.getLang());

        filterChain.doFilter(servletData.getServletRequest(), servletData.getServletResponse());
    }
}
