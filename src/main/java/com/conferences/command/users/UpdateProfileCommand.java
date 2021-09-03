package com.conferences.command.users;

import com.conferences.command.FrontCommand;
import com.conferences.config.Defaults;
import com.conferences.config.Error;
import com.conferences.entity.User;
import com.conferences.factory.HandlerFactory;
import com.conferences.factory.MapperFactory;
import com.conferences.factory.ServiceFactory;
import com.conferences.handler.abstraction.IUserDataSaver;
import com.conferences.mapper.IMapper;
import com.conferences.model.PasswordData;
import com.conferences.model.UserData;
import com.conferences.service.abstraction.IUserService;
import com.conferences.utils.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class UpdateProfileCommand extends FrontCommand {

    private final IUserService userService;
    private final IUserDataSaver userDataSaver;
    private final IMapper<HttpServletRequest, PasswordData> mapper;

    public UpdateProfileCommand() {
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
            Error error = validatePasswords(user.getPassword(), passwordData);
            if (error != Error.OK) {
                request.setAttribute("error", error.getByLang(lang));
                return;
            }
            user.setPassword(passwordData.getNewPassword());
        }
        updateUserWithRequestValues(user);
        if (userService.updateUser(user)) {
            redirectBack();
        } else {
            userDataSaver.restoreUserData(user, data);
            // TODO(unsuccessful update)
        }
    }

    private Error validatePasswords(String userPassword, PasswordData passwordData) {
        if (!userPassword.equals(passwordData.getPassword())) {
            return Error.INVALID_OLD_PASSWORD;
        }
        if (!passwordData.getNewPassword().equals(passwordData.getConfirmPassword())) {
            return Error.PASSWORDS_ARE_NOT_EQUAL;
        }
        return Error.OK;
    }

    private void updateUserWithRequestValues(User user) {
        user.setLogin(request.getParameter("login"));
        user.setSurname(request.getParameter("surname"));
        user.setName(request.getParameter("name"));
        user.setEmail(request.getParameter("email"));
    }
}
