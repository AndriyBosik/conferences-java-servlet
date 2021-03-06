package com.conferences.filter;

import com.conferences.config.Defaults;
import com.conferences.config.Page;
import com.conferences.model.ServletData;
import com.conferences.model.UrlData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public abstract class UrlDividerFilter extends DispatcherFilter {

    private static final Logger LOGGER = LogManager.getLogger(UrlDividerFilter.class);

    /**
     * <p>
     *     Divides URL into lang part and link part
     * </p>
     * @param servletRequest {@link ServletRequest}
     * @param servletResponse {@link ServletResponse}
     * @param filterChain {@link FilterChain}
     * @throws ServletException exception while may occur during filter processing
     * @throws IOException exception while may occur during filter processing
     */
    @Override
    public void processApp(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        ServletData servletData = new ServletData(servletRequest, servletResponse);

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String url = request.getRequestURI().substring(request.getContextPath().length());
        UrlData urlData = extractLanguage(url);
        LOGGER.info("Dividing url into parts: lang - {}, path - {}", urlData.getLang(), urlData.getPath());
        if (!checkLanguage(request, urlData.getLang())) {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            LOGGER.error("Page not found");
            return;
        }

        handleFilter(servletData, urlData, filterChain);
    }

    protected abstract void handleFilter(ServletData servletData, UrlData urlData, FilterChain filterChain) throws ServletException, IOException;

    /**
     * <p>
     *     Extracts language from URL
     * </p>
     * @param currentUrl string representing current URL
     * @return {@link UrlData}
     */
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

    /**
     * <p>
     *     Checks whatever language exists in {@link ServletContext}
     * </p>
     * @param request {@link ServletRequest}
     * @param language language that should be checked
     * @return true if language is present in {@link ServletContext}, false otherwise
     */
    private boolean checkLanguage(ServletRequest request, String language) {
        ServletContext context = request.getServletContext();
        List<String> languages = (List<String>) context.getAttribute("languages");
        return languages.contains(language.toLowerCase());
    }
}
