package com.conferences.command;

import com.conferences.config.Defaults;
import com.conferences.handler.LinkHandler;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class FrontCommand {

    protected ServletContext context;
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected LinkHandler linkHandler;
    protected String currentLang;

    public void init(ServletContext context, HttpServletRequest request, HttpServletResponse response) {
        this.context = context;
        this.request = request;
        this.response = response;
        this.linkHandler = new LinkHandler();
        this.currentLang = (String) request.getAttribute(Defaults.CURRENT_LANG.toString());
    }

    public abstract void process() throws ServletException, IOException;

    protected String getLayout() {
        return "main";
    }

    protected void forward(String target) throws ServletException, IOException {
        String view = String.format("/WEB-INF/jsp/%s.jsp", target);
        request.getRequestDispatcher(view).forward(request, response);
    }

    protected void forwardPartial(String view) throws ServletException, IOException {
        request.setAttribute("view", view);
        forward("layouts/" + getLayout());
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
