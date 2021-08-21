package com.conferences.command.home;

import com.conferences.command.FrontCommand;
import com.conferences.config.HttpMethods;
import com.conferences.config.Pages;
import com.conferences.helper.LangHelper;
import com.conferences.helper.PropertiesHelper;
import com.conferences.model.User;
import com.conferences.service.abstraction.IUserService;
import com.conferences.service.implementation.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class IndexCommand extends FrontCommand {
    private IUserService userService;
    private PropertiesHelper propertiesHelper;
    private LangHelper langHelper;

    public IndexCommand() {
        this.userService = new UserService();
        propertiesHelper = new PropertiesHelper();
        langHelper = new LangHelper();
    }

    @Override
    public void process() throws ServletException, IOException {
        String requestMethod = request.getMethod();
        if (requestMethod.equals(HttpMethods.GET.toString())) {
            forward("login");
        } else if (requestMethod.equals(HttpMethods.POST.toString())) {
            processLogin();
        }
    }

    private void processLogin() throws IOException, ServletException {
        if (!request.getMethod().equals("POST")) {
            return;
        }
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        User user = userService.getByLoginAndPasswordWithRole(login, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            response.sendRedirect(Pages.PROFILE.toString());
        } else {
            request.setAttribute("errorMessage", propertiesHelper.getPropertyValue("messages", langHelper.getLangFromSession(request), "errors.invalid_login_or_password"));

            forward("login");
        }
    }
}
