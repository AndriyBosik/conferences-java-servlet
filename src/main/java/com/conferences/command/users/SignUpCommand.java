package com.conferences.command.users;

import com.conferences.command.FrontCommand;
import com.conferences.config.*;
import com.conferences.entity.Role;
import com.conferences.entity.User;
import com.conferences.factory.MapperFactory;
import com.conferences.factory.ServiceFactory;
import com.conferences.mapper.IMapper;
import com.conferences.model.FormError;
import com.conferences.model.UserData;
import com.conferences.service.abstraction.IRoleService;
import com.conferences.service.abstraction.IUserService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignUpCommand extends FrontCommand {

    private IUserService userService;
    private IRoleService roleService;
    private IMapper<HttpServletRequest, UserData> mapper;

    @Override
    public void init(ServletContext context, HttpServletRequest request, HttpServletResponse response, List<String> urlParams) {
        super.init(context, request, response, urlParams);
        userService = ServiceFactory.getInstance().getUserService();
        roleService = ServiceFactory.getInstance().getRoleService();
        mapper = MapperFactory.getInstance().getRequestToUserDataMapper();
    }

    @Override
    public void process() throws ServletException, IOException {
        if (request.getMethod().equals(HttpMethod.GET.toString())) {
            extractErrorsFromSession(FormKeys.REGISTRATION_ERRORS);
            extractFieldValuesFromSession(FormKeys.REGISTRATION_FIELDS);

            forward("sign_up");
            return;
        }
        signUp();
    }

    private void signUp() throws IOException, ServletException {
        User user = new User();
        Role role = roleService.getRoleByTitle(request.getParameter("role"));

        UserData data = mapper.map(request);
        user.setLogin(data.getLogin());
        user.setPassword(data.getPassword());
        user.setSurname(data.getSurname());
        user.setName(data.getName());
        user.setEmail(data.getEmail());
        user.setRole(role);

        data.setConfirmPassword(request.getParameter("confirm_password"));

        List<FormError> errors = new ArrayList<>();
        if (data.getConfirmPassword() == null || data.getConfirmPassword().trim().isEmpty()) {
            errors.add(new FormError(ErrorKey.EMPTY_PASSWORD_FIELD));
            saveErrorsToSession(FormKeys.REGISTRATION_ERRORS, errors);
            redirect(Page.SIGN_UP_USER.toString());
            return;
        }
        if (!data.getConfirmPassword().equals(user.getPassword())) {
            errors.add(new FormError(ErrorKey.PASSWORDS_ARE_NOT_EQUAL));
            saveErrorsToSession(FormKeys.REGISTRATION_ERRORS, errors);
            redirect(Page.SIGN_UP_USER.toString());
            return;
        }

        errors = userService.signUpUser(user);
        if (errors.isEmpty()) {
            request.getSession().setAttribute(Defaults.USER.toString(), user);
            redirect(Page.PROFILE.toString());
        } else {
            saveErrorsToSession(FormKeys.REGISTRATION_ERRORS, errors);
            System.out.println(errors);
            Map<String, String> values = new HashMap<>();
            values.put("login", user.getLogin());
            values.put("email", user.getEmail());
            values.put("name", user.getName());
            values.put("surname", user.getSurname());
            saveFieldValuesToSession(FormKeys.REGISTRATION_FIELDS, values);

            redirect(Page.SIGN_UP_USER.toString());
        }
    }
}
