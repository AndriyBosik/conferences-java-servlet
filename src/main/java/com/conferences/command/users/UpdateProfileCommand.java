package com.conferences.command.users;

import com.conferences.command.FrontCommand;
import com.conferences.config.Defaults;
import com.conferences.config.Errors;
import com.conferences.entity.User;
import com.conferences.handler.abstraction.IEncodingHandler;
import com.conferences.handler.abstraction.IUserDataSaver;
import com.conferences.handler.implementation.EncodingHandler;
import com.conferences.handler.implementation.UserDataSaver;
import com.conferences.model.UserData;
import com.conferences.service.abstraction.IUserService;
import com.conferences.service.implementation.UserService;
import com.conferences.utils.StringUtils;

import javax.servlet.ServletException;
import java.io.IOException;

public class UpdateProfileCommand extends FrontCommand {

    private final IUserService userService;
    private final IEncodingHandler encodingHandler;
    private final IUserDataSaver userDataSaver;

    public UpdateProfileCommand() {
        userService = new UserService();
        encodingHandler = new EncodingHandler();
        userDataSaver = new UserDataSaver();
    }

    @Override
    public void process() throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute(Defaults.USER.toString());
        UserData data = userDataSaver.saveUserData(user);
        String lang = (String) request.getAttribute(Defaults.CURRENT_LANG.toString());
        String password = encodingHandler.getUTF8ValueFromRequest(request, "password");
        String newPassword = encodingHandler.getUTF8ValueFromRequest(request, "new-password");
        String confirmPassword = encodingHandler.getUTF8ValueFromRequest(request, "confirm-password");
        if (!StringUtils.isNullOrEmptyAll(password, newPassword, confirmPassword)) {
            Errors error = validatePasswords(user.getPassword(), password, newPassword, confirmPassword);
            if (error != Errors.OK) {
                request.setAttribute("error", error.getByLang(lang));
                return;
            }
            user.setPassword(newPassword);
        }
        updateUserWithRequestValues(user);
        if (userService.updateUser(user)) {
            redirectBack();
        } else {
            userDataSaver.restoreUserData(user, data);
            // TODO(unsuccessful update)
        }
    }

    private Errors validatePasswords(String userPassword, String password, String newPassword, String confirmPassword) {
        if (!userPassword.equals(password)) {
            return Errors.INVALID_OLD_PASSWORD;
        }
        if (!newPassword.equals(confirmPassword)) {
            return Errors.PASSWORDS_ARE_NOT_EQUAL;
        }
        return Errors.OK;
    }

    private void updateUserWithRequestValues(User user) {
        user.setLogin(encodingHandler.getUTF8ValueFromRequest(request, "login"));
        user.setSurname(encodingHandler.getUTF8ValueFromRequest(request, "surname"));
        user.setName(encodingHandler.getUTF8ValueFromRequest(request, "name"));
        user.setEmail(encodingHandler.getUTF8ValueFromRequest(request, "email"));
    }
}
