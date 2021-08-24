package com.conferences.command;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class FrontCommand {

    protected ServletContext context;
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    public void init(ServletContext context, HttpServletRequest request, HttpServletResponse response) {
        this.context = context;
        this.request = request;
        this.response = response;
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

}
