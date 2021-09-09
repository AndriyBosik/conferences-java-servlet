package com.conferences.command;

import com.conferences.config.Defaults;
import com.conferences.factory.HandlerFactory;
import com.conferences.factory.MapperFactory;
import com.conferences.handler.abstraction.ILinkHandler;
import com.conferences.mapper.IMapper;
import com.conferences.model.FormError;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *      Represents command which can process HTTP requests
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public abstract class FrontCommand {

    private static final Logger LOGGER = LogManager.getLogger(FrontCommand.class);

    protected ServletContext context;
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected String currentLang;
    protected ILinkHandler linkHandler;
    protected IMapper<FormError, String> formErrorMapper;

    public void init(ServletContext context, HttpServletRequest request, HttpServletResponse response, List<String> urlParams) throws IOException {
        this.context = context;
        this.request = request;
        this.response = response;
        this.formErrorMapper = MapperFactory.getInstance().getFormErrorToStringMapper();
        this.linkHandler = HandlerFactory.getInstance().getLinkHandler();
        this.currentLang = (String) request.getAttribute(Defaults.CURRENT_LANG.toString());
    }

    public abstract void process() throws ServletException, IOException;

    /**
     * @return layout for pages
     */
    protected String getLayout() {
        return "main";
    }

    /**
     * <p>
     *     Saves list of errors to session
     * </p>
     * @param key key to store errors in
     * @param formErrors list of errors
     * @throws ServletException an exception which may occur during saving errors to session
     * @throws IOException an exception which may occur during saving errors to session
     */
    protected void saveErrorsToSession(String key, List<FormError> formErrors) throws ServletException, IOException {
        String lang = (String) request.getAttribute(Defaults.CURRENT_LANG.toString());
        List<String> errors = formErrors.stream()
                .map(error -> {
                    error.setLang(lang);
                    return formErrorMapper.map(error);})
                .collect(Collectors.toList());
        request.getSession().setAttribute(key, errors);
    }

    /**
     * <p>
     *     Extracts errors from session to request
     * </p>
     * @param key key to extract errors from session by
     */
    protected void extractErrorsFromSession(String key) {
        List<String> errors = (List<String>) request.getSession().getAttribute(key);
        request.getSession().removeAttribute(key);
        request.setAttribute(key, errors);
    }

    /**
     * <p>
     *     Saves form field values to session
     * </p>
     * @param key key to save errors in
     * @param values fields values
     */
    protected void saveFieldValuesToSession(String key, Map<String, String> values) {
        request.getSession().setAttribute(key, values);
    }

    /**
     * <p>
     *     Extracts form fields from session to request
     * </p>
     * @param key key to extract values by
     */
    protected void extractFieldValuesFromSession(String key) {
        Object values = request.getSession().getAttribute(key);
        request.getSession().removeAttribute(key);
        request.setAttribute(key, values);
    }

    /**
     * <p>
     *     Forwards request to passed JSP page
     * </p>
     * @param target JSP page to pass request to
     * @throws ServletException an exception which may occur during saving errors to session
     * @throws IOException an exception which may occur during saving errors to session
     */
    protected void forward(String target) throws ServletException, IOException {
        String view = String.format("/WEB-INF/jsp/%s.jsp", target);
        request.getRequestDispatcher(view).forward(request, response);
    }

    /**
     * <p>
     *     Forwards request to partial view with specific title
     * </p>
     * @param view partial view
     * @param title view title
     * @throws ServletException an exception which may occur during saving errors to session
     * @throws IOException an exception which may occur during saving errors to session
     */
    protected void forwardPartial(String view, String title) throws ServletException, IOException {
        request.setAttribute("view", view);
        request.setAttribute("title", title);
        forward("layouts/" + getLayout());
    }

    /**
     * <p>
     *     Forwards request to partial view
     * </p>
     * @param view partial view
     * @throws ServletException an exception which may occur during saving errors to session
     * @throws IOException an exception which may occur during saving errors to session
     */
    protected void forwardPartial(String view) throws ServletException, IOException {
        forwardPartial(view, "");
    }

    /**
     * <p>
     *     Redirects user to another URL
     * </p>
     * @param url url to redirect to
     * @throws IOException an exception which may occur during saving errors to session
     */
    protected void redirect(String url) throws IOException {
        String link = linkHandler.addLangToUrl(url, currentLang);
        response.sendRedirect(link);
    }

    /**
     * <p>
     *     Redirects user to previous page
     * </p>
     */
    protected void redirectBack() {
        try {
            response.sendRedirect(request.getHeader("referer"));
        } catch (IOException exception) {
            LOGGER.error("Unable to redirect", exception);
        }
    }

}
