package com.conferences.filter;

import com.conferences.model.ServletData;
import com.conferences.model.UrlData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;

public class UrlModifierFilter extends UrlDividerFilter {

    private static final Logger LOGGER = LogManager.getLogger(UrlModifierFilter.class);

    @Override
    protected void handleFilter(ServletData servletData, UrlData urlData, FilterChain filterChain) throws ServletException, IOException {
        String url = "/app" + urlData.getPath();
        LOGGER.info("Forwarding request to {}", url);
        servletData.getServletRequest()
            .getRequestDispatcher(url)
            .forward(
                servletData.getServletRequest(),
                servletData.getServletResponse()
            );
    }
}
