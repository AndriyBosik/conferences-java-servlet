package com.conferences.filter;

import com.conferences.config.Defaults;
import com.conferences.model.ServletData;
import com.conferences.model.UrlData;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <p>
 *     Adds addition information about URL to request attributes
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public class LinkFilter extends UrlDividerFilter {

    /**
     * <p>
     *     Adds addition information about URL to request attributes
     * </p>
     * @param servletData data about request, response and context
     * @param urlData data about url
     * @param filterChain {@link FilterChain}
     * @throws ServletException exception while may occur during filter processing
     * @throws IOException exception while may occur during filter processing
     */
    @Override
    protected void handleFilter(ServletData servletData, UrlData urlData, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletData.getServletRequest();

        request.setAttribute(Defaults.CURRENT_LANG.toString(), urlData.getLang());
        request.setAttribute(Defaults.CURRENT_LINK.toString(), urlData.getPath());

        filterChain.doFilter(servletData.getServletRequest(), servletData.getServletResponse());
    }
}
