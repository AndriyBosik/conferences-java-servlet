package com.conferences.mapper;

import com.conferences.model.PasswordData;

import javax.servlet.http.HttpServletRequest;

/**
 * {@inheritDoc}
 */
public class RequestToPasswordDataMapper implements IMapper<HttpServletRequest, PasswordData> {

    /**
     * <p>
     *     Maps {@link HttpServletRequest} to {@link PasswordData}
     * </p>
     */
    @Override
    public PasswordData map(HttpServletRequest request) {
        PasswordData data = new PasswordData();
        data.setPassword(request.getParameter("password"));
        data.setNewPassword(request.getParameter("new-password"));
        data.setConfirmPassword(request.getParameter("confirm-password"));
        return data;
    }
}
