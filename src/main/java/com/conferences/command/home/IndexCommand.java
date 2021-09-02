package com.conferences.command.home;

import com.conferences.command.FrontCommand;
import com.conferences.config.Defaults;
import com.conferences.config.Errors;
import com.conferences.config.HttpMethod;
import com.conferences.config.Pages;
import com.conferences.entity.User;
import com.conferences.mapper.IMapper;
import com.conferences.mapper.RequestToLoginDataMapper;
import com.conferences.model.LoginData;
import com.conferences.service.abstraction.IUserService;
import com.conferences.service.implementation.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class IndexCommand extends FrontCommand {

    private final IUserService userService;
    private final IMapper<HttpServletRequest, LoginData> mapper;

    public IndexCommand() {
        this.userService = new UserService();
        this.mapper = new RequestToLoginDataMapper();
    }

    @Override
    public void process() throws ServletException, IOException {
        String requestMethod = request.getMethod();
        if (requestMethod.equals(HttpMethod.GET.toString())) {
            forward("login");
        } else if (requestMethod.equals(HttpMethod.POST.toString())) {
            processLogin();
        }
    }

    private void processLogin() throws IOException, ServletException {
        if (!request.getMethod().equals("POST")) {
            return;
        }
        String lang = (String) request.getAttribute(Defaults.CURRENT_LANG.toString());

        LoginData loginData = mapper.map(request);

        User user = userService.getByLoginAndPasswordWithRole(loginData.getLogin(), loginData.getPassword());

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute(Defaults.USER.toString(), user);

            redirect(Pages.PROFILE.toString());
        } else {
            request.setAttribute("errorMessage", Errors.INVALID_LOGIN_OR_PASSWORD.getByLang(lang));

            forward("login");
        }
    }
}
