package com.conferences.command.users;

import com.conferences.command.FrontCommand;
import com.conferences.config.Defaults;
import com.conferences.config.HttpMethod;
import com.conferences.config.Pages;
import com.conferences.entity.Role;
import com.conferences.entity.User;
import com.conferences.service.abstraction.IRoleService;
import com.conferences.service.abstraction.IUserService;
import com.conferences.service.implementation.RoleService;
import com.conferences.service.implementation.UserService;

import javax.servlet.ServletException;
import java.io.IOException;

public class SignUpCommand extends FrontCommand {

    private IUserService userService;
    private IRoleService roleService;

    public SignUpCommand() {
        userService = new UserService();
        roleService = new RoleService();
    }

    @Override
    public void process() throws ServletException, IOException {
        if (request.getMethod().equals(HttpMethod.GET.toString())) {
            forward("sign_up");
            return;
        }
        signUp();
    }

    private void signUp() throws IOException {
        User user = new User();
        Role role = roleService.getRoleByTitle(request.getParameter("role"));

        user.setLogin(request.getParameter("login"));
        user.setPassword(request.getParameter("password"));
        user.setSurname(request.getParameter("surname"));
        user.setName(request.getParameter("name"));
        user.setEmail(request.getParameter("email"));
        user.setRole(role);

        String confirmedPassword = request.getParameter("confirm_password");
        if (!confirmedPassword.equals(user.getPassword())) {
            redirect(Pages.SIGN_UP_USER.toString());
            return;
        }

        if (userService.signUpUser(user)) {
            request.getSession().setAttribute(Defaults.USER.toString(), user);

            redirect(Pages.PROFILE.toString());
        } else {
            // TODO
        }
    }
}
