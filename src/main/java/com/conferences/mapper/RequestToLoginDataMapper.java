package com.conferences.mapper;

import com.conferences.handler.abstraction.IEncodingHandler;
import com.conferences.handler.implementation.EncodingHandler;
import com.conferences.model.LoginData;

import javax.servlet.http.HttpServletRequest;

public class RequestToLoginDataMapper implements IMapper<HttpServletRequest, LoginData> {

    private IEncodingHandler encodingHandler;

    public RequestToLoginDataMapper() {
        encodingHandler = new EncodingHandler();
    }

    @Override
    public LoginData map(HttpServletRequest request) {
        LoginData loginData = new LoginData();
        loginData.setLogin(encodingHandler.getUTF8ValueFromRequest(request, "login"));
        loginData.setPassword(encodingHandler.getUTF8ValueFromRequest(request, "password"));
        return loginData;
    }
}
