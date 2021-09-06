package com.conferences.model;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class ServletData {

    private ServletRequest servletRequest;

    private ServletResponse servletResponse;

    public ServletData() {}

    public ServletData(ServletRequest servletRequest, ServletResponse servletResponse) {
        this.servletRequest = servletRequest;
        this.servletResponse = servletResponse;
    }

    public ServletRequest getServletRequest() {
        return servletRequest;
    }

    public void setServletRequest(ServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }

    public ServletResponse getServletResponse() {
        return servletResponse;
    }

    public void setServletResponse(ServletResponse servletResponse) {
        this.servletResponse = servletResponse;
    }
}
