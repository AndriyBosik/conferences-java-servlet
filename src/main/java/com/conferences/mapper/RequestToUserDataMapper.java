package com.conferences.mapper;

import com.conferences.handler.abstraction.IEncodingHandler;
import com.conferences.handler.implementation.EncodingHandler;
import com.conferences.model.UserData;

import javax.servlet.http.HttpServletRequest;

public class RequestToUserDataMapper implements IMapper<HttpServletRequest, UserData> {

    private final IEncodingHandler encodingHandler;

    public RequestToUserDataMapper() {
        encodingHandler = new EncodingHandler();
    }

    @Override
    public UserData map(HttpServletRequest request) {
        UserData data = new UserData();

        data.setLogin(encodingHandler.getUTF8ValueFromRequest(request, "login"));
        data.setPassword(encodingHandler.getUTF8ValueFromRequest(request, "password"));
        data.setSurname(encodingHandler.getUTF8ValueFromRequest(request, "surname"));
        data.setName(encodingHandler.getUTF8ValueFromRequest(request, "name"));
        data.setEmail(encodingHandler.getUTF8ValueFromRequest(request, "email"));

        return data;
    }
}
