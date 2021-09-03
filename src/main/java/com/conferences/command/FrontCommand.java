package com.conferences.command;

import com.conferences.config.Defaults;
import com.conferences.factory.HandlerFactory;
import com.conferences.handler.abstraction.ILinkHandler;
import com.conferences.handler.implementation.LinkHandler;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class FrontCommand {

    protected ServletContext context;
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected ILinkHandler linkHandler;
    protected String currentLang;

    public void init(ServletContext context, HttpServletRequest request, HttpServletResponse response) {
        this.context = context;
        this.request = request;
        this.response = response;
        this.linkHandler = HandlerFactory.getInstance().getLinkHandler();
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
