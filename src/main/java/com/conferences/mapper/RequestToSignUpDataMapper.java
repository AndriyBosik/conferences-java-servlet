package com.conferences.mapper;

import com.conferences.handler.abstraction.IEncodingHandler;
import com.conferences.handler.implementation.EncodingHandler;
import com.conferences.model.SignUpData;

import javax.servlet.http.HttpServletRequest;

public class RequestToSignUpDataMapper implements IMapper<HttpServletRequest, SignUpData> {

    private final IEncodingHandler encodingHandler;

    public RequestToSignUpDataMapper() {
        encodingHandler = new EncodingHandler();
    }

    @Override
    public SignUpData map(HttpServletRequest request) {
        SignUpData data = new SignUpData();

        data.setLogin(encodingHandler.getUTF8ValueFromRequest(request, "login"));
        data.setPassword(encodingHandler.getUTF8ValueFromRequest(request, "password"));
        data.setSurname(encodingHandler.getUTF8ValueFromRequest(request, "surname"));
        data.setName(encodingHandler.getUTF8ValueFromRequest(request, "name"));
        data.setEmail(encodingHandler.getUTF8ValueFromRequest(request, "email"));

        return data;
    }
}
