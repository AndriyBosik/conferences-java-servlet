package com.conferences.command;

import com.conferences.config.Defaults;
import com.conferences.factory.HandlerFactory;
import com.conferences.factory.MapperFactory;
import com.conferences.handler.abstraction.ILinkHandler;
import com.conferences.mapper.IMapper;
import com.conferences.model.FormError;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class FrontCommand {

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

    protected String getLayout() {
        return "main";
    }

    protected void saveErrorsToSession(String key, List<FormError> formErrors) throws ServletException, IOException {
        String lang = (String) request.getAttribute(Defaults.CURRENT_LANG.toString());
        List<String> errors = formErrors.stream()
                .map(error -> {
                    error.setLang(lang);
                    return formErrorMapper.map(error);})
                .collect(Collectors.toList());
        request.getSession().setAttribute(key, errors);
    }

    protected void extractErrorsFromSession(String key) {
        List<String> errors = (List<String>) request.getSession().getAttribute(key);
        request.getSession().removeAttribute(key);
        request.setAttribute(key, errors);
    }

    protected void saveFieldValuesToSession(String key, Map<String, String> values) {
        request.getSession().setAttribute(key, values);
    }

    protected void extractFieldValuesFromSession(String key) {
        Object values = request.getSession().getAttribute(key);
        request.getSession().removeAttribute(key);
        request.setAttribute(key, values);
    }

    protected void forward(String target) throws ServletException, IOException {
        String view = String.format("/WEB-INF/jsp/%s.jsp", target);
        request.getRequestDispatcher(view).forward(request, response);
    }

    protected void forwardBadRequest() {
        // TODO(implement bad request layout)
    }

    protected void forwardPartial(String view, String title) throws ServletException, IOException {
        request.setAttribute("view", view);
        request.setAttribute("title", title);
        forward("layouts/" + getLayout());
    }

    protected void forwardPartial(String view) throws ServletException, IOException {
        forwardPartial(view, "");
    }

    protected void redirect(String url) throws IOException {
        String link = linkHandler.addLangToUrl(url, currentLang);
        response.sendRedirect(link);
    }

    protected void redirectBack() {
        try {
            response.sendRedirect(request.getHeader("referer"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
