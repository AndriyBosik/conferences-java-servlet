package com.conferences.command.home;

import com.conferences.command.FrontCommand;
import com.conferences.config.Defaults;
import com.conferences.config.Error;
import com.conferences.config.HttpMethod;
import com.conferences.config.Page;
import com.conferences.entity.User;
import com.conferences.factory.MapperFactory;
import com.conferences.factory.ServiceFactory;
import com.conferences.mapper.IMapper;
import com.conferences.model.LoginData;
import com.conferences.service.abstraction.IUserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class IndexCommand extends FrontCommand {

    private final IUserService userService;
    private final IMapper<HttpServletRequest, LoginData> mapper;

    public IndexCommand() {
        this.userService = ServiceFactory.getInstance().getUserService();
        this.mapper = MapperFactory.getInstance().getRequestToLoginDataMapper();
    }

    @Override
    public void process() throws ServletException, IOException {
        String requestMethod = request.getMethod();
        if (requestMethod.equals(HttpMethod.GET.toString())) {
            User user = (User) request.getSession().getAttribute(Defaults.USER.toString());
            if (user != null) {
                redirect(Page.PROFILE.toString());
                return;
            }

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

            redirect(Page.PROFILE.toString());
        } else {
            request.setAttribute("errorMessage", Error.INVALID_LOGIN_OR_PASSWORD.getByLang(lang));

            forward("login");
        }
    }
}
