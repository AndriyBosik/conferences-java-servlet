package com.conferences.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *     Listeners calling when ServletContext is initialized
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public class AppContextListener implements ServletContextListener {

    private static final String LOCALES_CONTEXT_PARAM = "languages";

    /**
     * <p>
     *     Sets available languages list to context attribute
     * </p>
     * @param event
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        String localesAsString = context.getInitParameter(LOCALES_CONTEXT_PARAM);

        List<String> locales = Arrays.asList(localesAsString.split(" "));

        context.setAttribute(LOCALES_CONTEXT_PARAM, locales);
    }
}
