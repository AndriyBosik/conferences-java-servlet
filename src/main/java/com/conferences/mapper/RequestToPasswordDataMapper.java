package com.conferences.mapper;

import com.conferences.model.PasswordData;

import javax.servlet.http.HttpServletRequest;

public class RequestToPasswordDataMapper implements IMapper<HttpServletRequest, PasswordData> {

    @Override
    public PasswordData map(HttpServletRequest request) {
        PasswordData data = new PasswordData();
        data.setPassword(request.getParameter("password"));
        data.setNewPassword(request.getParameter("new-password"));
        data.setConfirmPassword(request.getParameter("confirm-password"));
        return data;
    }
}
