package com.conferences.mapper;

import com.conferences.model.UserData;

import javax.servlet.http.HttpServletRequest;

public class RequestToUserDataMapper implements IMapper<HttpServletRequest, UserData> {

    @Override
    public UserData map(HttpServletRequest request) {
        UserData data = new UserData();

        data.setLogin(request.getParameter("login"));
        data.setPassword(request.getParameter("password"));
        data.setSurname(request.getParameter("surname"));
        data.setName(request.getParameter("name"));
        data.setEmail(request.getParameter("email"));

        return data;
    }
}
