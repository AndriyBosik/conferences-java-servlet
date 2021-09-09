package com.conferences.mapper;

import com.conferences.model.LoginData;

import javax.servlet.http.HttpServletRequest;

/**
 * {@inheritDoc}
 */
public class RequestToLoginDataMapper implements IMapper<HttpServletRequest, LoginData> {

    /**
     * <p>
     *     Maps {@link HttpServletRequest} to {@link LoginData}
     * </p>
     */
    @Override
    public LoginData map(HttpServletRequest request) {
        LoginData loginData = new LoginData();
        loginData.setLogin(request.getParameter("login"));
        loginData.setPassword(request.getParameter("password"));
        return loginData;
    }
}
