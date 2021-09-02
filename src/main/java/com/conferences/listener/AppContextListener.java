package com.conferences.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Arrays;
import java.util.List;

public class AppContextListener implements ServletContextListener {

    private static final String LOCALES_CONTEXT_PARAM = "languages";

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        String localesAsString = context.getInitParameter(LOCALES_CONTEXT_PARAM);

        List<String> locales = Arrays.asList(localesAsString.split(" "));

        context.setAttribute(LOCALES_CONTEXT_PARAM, locales);
    }
}
