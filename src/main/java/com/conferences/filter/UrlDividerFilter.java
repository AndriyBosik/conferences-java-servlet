package com.conferences.filter;

import com.conferences.config.Defaults;
import com.conferences.config.Page;
import com.conferences.filter.model.ServletData;
import com.conferences.filter.model.UrlData;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public abstract class UrlDividerFilter extends DispatcherFilter {
    @Override
    public void processApp(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        ServletData servletData = new ServletData(servletRequest, servletResponse);

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        UrlData urlData = extractLanguage(request.getRequestURI().substring(request.getContextPath().length()));
        if (!checkLanguage(request, urlData.getLang())) {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        handleFilter(servletData, urlData, filterChain);
    }

    protected abstract void handleFilter(ServletData servletData, UrlData urlData, FilterChain filterChain) throws ServletException, IOException;

    private UrlData extractLanguage(String currentUrl) {
        if (Page.HOME.toString().equals(currentUrl)) {
            return new UrlData(Defaults.DEFAULT_LANG.toString(), currentUrl);
        }

        if (currentUrl.startsWith("/")) {
            currentUrl = currentUrl.substring(1);
        }

        StringBuilder url = new StringBuilder();

        String[] parts = currentUrl.split("/");
        for (int i = 1; i < parts.length; i++) {
            url.append("/").append(parts[i]);
        }
        if (url.length() == 0) {
            url.append("/");
        }
        return new UrlData(parts[0].toLowerCase(), url.toString());
    }

    private boolean checkLanguage(ServletRequest request, String language) {
        ServletContext context = request.getServletContext();
        List<String> languages = (List<String>) context.getAttribute("languages");
        return languages.contains(language.toLowerCase());
    }
}
