package com.conferences.command.home;

import com.conferences.command.FrontCommand;
import com.conferences.config.Defaults;
import com.conferences.config.ErrorKey;
import com.conferences.config.HttpMethod;
import com.conferences.config.Page;
import com.conferences.entity.User;
import com.conferences.factory.MapperFactory;
import com.conferences.factory.ServiceFactory;
import com.conferences.mapper.IMapper;
import com.conferences.model.LoginData;
import com.conferences.service.abstraction.IAuthenticationService;
import com.conferences.service.abstraction.IUserService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class IndexCommand extends FrontCommand {

    private IUserService userService;
    private IAuthenticationService authenticationService;
    private IMapper<HttpServletRequest, LoginData> mapper;

    @Override
    public void init(ServletContext context, HttpServletRequest request, HttpServletResponse response, List<String> urlParams) throws IOException {
        super.init(context, request, response, urlParams);
        userService = ServiceFactory.getInstance().getUserService();
        authenticationService = ServiceFactory.getInstance().getAuthenticationService();
        mapper = MapperFactory.getInstance().getRequestToLoginDataMapper();
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

        User user = authenticationService.getByLoginAndPasswordWithRole(loginData.getLogin(), loginData.getPassword());

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute(Defaults.USER.toString(), user);

            redirect(Page.PROFILE.toString());
        } else {
            request.setAttribute("errorMessage", ErrorKey.INVALID_LOGIN_OR_PASSWORD.getByLang(lang));

            forward("login");
        }
    }
}
