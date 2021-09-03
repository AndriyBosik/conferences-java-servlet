package com.conferences.command.users;

import com.conferences.command.FrontCommand;
import com.conferences.config.Defaults;
import com.conferences.config.HttpMethod;
import com.conferences.config.Page;
import com.conferences.entity.Role;
import com.conferences.entity.User;
import com.conferences.factory.MapperFactory;
import com.conferences.factory.ServiceFactory;
import com.conferences.mapper.IMapper;
import com.conferences.model.UserData;
import com.conferences.service.abstraction.IRoleService;
import com.conferences.service.abstraction.IUserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class SignUpCommand extends FrontCommand {

    private final IUserService userService;
    private final IRoleService roleService;
    private final IMapper<HttpServletRequest, UserData> mapper;

    public SignUpCommand() {
        userService = ServiceFactory.getInstance().getUserService();
        roleService = ServiceFactory.getInstance().getRoleService();
        mapper = MapperFactory.getInstance().getRequestToUserDataMapper();
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

        UserData data = mapper.map(request);
        user.setLogin(data.getLogin());
        user.setPassword(data.getPassword());
        user.setSurname(data.getSurname());
        user.setName(data.getName());
        user.setEmail(data.getEmail());
        user.setRole(role);

        if (!data.getConfirmPassword().equals(user.getPassword())) {
            redirect(Page.SIGN_UP_USER.toString());
            return;
        }

        if (userService.signUpUser(user)) {
            request.getSession().setAttribute(Defaults.USER.toString(), user);

            redirect(Page.PROFILE.toString());
        } else {
            // TODO
        }
    }
}
