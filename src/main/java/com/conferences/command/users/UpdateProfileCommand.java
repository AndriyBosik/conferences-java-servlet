package com.conferences.command.users;

import com.conferences.command.FrontCommand;
import com.conferences.config.Defaults;
import com.conferences.config.ErrorKey;
import com.conferences.config.FormKeys;
import com.conferences.entity.User;
import com.conferences.factory.HandlerFactory;
import com.conferences.factory.MapperFactory;
import com.conferences.factory.ServiceFactory;
import com.conferences.handler.abstraction.IUserDataSaver;
import com.conferences.mapper.IMapper;
import com.conferences.model.FormError;
import com.conferences.model.PasswordData;
import com.conferences.model.UserData;
import com.conferences.service.abstraction.IUserService;
import com.conferences.utils.StringUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UpdateProfileCommand extends FrontCommand {

    private IUserService userService;
    private IUserDataSaver userDataSaver;
    private IMapper<HttpServletRequest, PasswordData> mapper;

    @Override
    public void init(ServletContext context, HttpServletRequest request, HttpServletResponse response, List<String> urlParams) {
        super.init(context, request, response, urlParams);
        userService = ServiceFactory.getInstance().getUserService();
        userDataSaver = HandlerFactory.getInstance().getUserDataSaver();
        mapper = MapperFactory.getInstance().getRequestToPasswordDataMapper();
    }

    @Override
    public void process() throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute(Defaults.USER.toString());
        UserData data = userDataSaver.saveUserData(user);
        String lang = (String) request.getAttribute(Defaults.CURRENT_LANG.toString());
        PasswordData passwordData = mapper.map(request);
        if (!StringUtils.isNullOrEmptyAll(passwordData.getPassword(), passwordData.getNewPassword(), passwordData.getConfirmPassword())) {
            ErrorKey errorKey = validatePasswords(user.getPassword(), passwordData);
            if (errorKey != ErrorKey.OK) {
                request.setAttribute("error", errorKey.getByLang(lang));
                return;
            }
            user.setPassword(passwordData.getNewPassword());
        }
        updateUserWithRequestValues(user);
        List<FormError> errors = userService.updateUser(user);
        if (!errors.isEmpty()) {
            userDataSaver.restoreUserData(user, data);
            saveErrorsToSession(FormKeys.PROFILE_ERRORS, errors);
        }
        redirectBack();
    }

    private ErrorKey validatePasswords(String userPassword, PasswordData passwordData) {
        if (!userPassword.equals(passwordData.getPassword())) {
            return ErrorKey.INVALID_OLD_PASSWORD;
        }
        if (!passwordData.getNewPassword().equals(passwordData.getConfirmPassword())) {
            return ErrorKey.PASSWORDS_ARE_NOT_EQUAL;
        }
        return ErrorKey.OK;
    }

    private void updateUserWithRequestValues(User user) {
        user.setLogin(request.getParameter("login"));
        user.setSurname(request.getParameter("surname"));
        user.setName(request.getParameter("name"));
        user.setEmail(request.getParameter("email"));
    }
}
