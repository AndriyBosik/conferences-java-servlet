package com.conferences.command.home;

import com.conferences.command.FrontCommand;
import com.conferences.config.HttpMethod;
import com.conferences.config.Pages;
import com.conferences.helper.LangHelper;
import com.conferences.helper.LinkHelper;
import com.conferences.helper.PropertiesHelper;
import com.conferences.entity.User;
import com.conferences.service.abstraction.IUserService;
import com.conferences.service.implementation.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class IndexCommand extends FrontCommand {
    private IUserService userService;
    private PropertiesHelper propertiesHelper;
    private LangHelper langHelper;
    private LinkHelper linkHelper;

    public IndexCommand() {
        this.userService = new UserService();
        this.propertiesHelper = new PropertiesHelper();
        this.langHelper = new LangHelper();
        this.linkHelper = new LinkHelper();
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
        String lang = langHelper.getLangFromSession(request.getSession());

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        User user = userService.getByLoginAndPasswordWithRole(login, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            response.sendRedirect(linkHelper.addLangToUrl(Pages.PROFILE.toString(), lang));
        } else {
            request.setAttribute("errorMessage", propertiesHelper.getPropertyValue("messages", lang, "errors.invalid_login_or_password"));

            forward("login");
        }
    }
}
