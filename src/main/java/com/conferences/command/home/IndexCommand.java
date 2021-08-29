package com.conferences.command.home;

import com.conferences.command.FrontCommand;
import com.conferences.config.Defaults;
import com.conferences.config.Errors;
import com.conferences.config.HttpMethod;
import com.conferences.config.Pages;
import com.conferences.handler.LangHandler;
import com.conferences.handler.LinkHandler;
import com.conferences.handler.PropertiesHandler;
import com.conferences.entity.User;
import com.conferences.service.abstraction.IUserService;
import com.conferences.service.implementation.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class IndexCommand extends FrontCommand {
    private IUserService userService;

    public IndexCommand() {
        this.userService = new UserService();
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

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        User user = userService.getByLoginAndPasswordWithRole(login, password);

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
