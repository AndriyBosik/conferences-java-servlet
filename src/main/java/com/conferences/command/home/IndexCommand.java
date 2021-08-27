package com.conferences.command.home;

import com.conferences.command.FrontCommand;
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
    private PropertiesHandler propertiesHandler;
    private LangHandler langHandler;
    private LinkHandler linkHandler;

    public IndexCommand() {
        this.userService = new UserService();
        this.propertiesHandler = new PropertiesHandler();
        this.langHandler = new LangHandler();
        this.linkHandler = new LinkHandler();
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
        String lang = langHandler.getLang(request.getSession());

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        User user = userService.getByLoginAndPasswordWithRole(login, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            response.sendRedirect(linkHandler.addLangToUrl(Pages.PROFILE.toString(), lang));
        } else {
            request.setAttribute("errorMessage", propertiesHandler.getPropertyValue("messages", lang, "errors.invalid_login_or_password"));

            forward("login");
        }
    }
}
