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
import com.conferences.model.FormError;
import com.conferences.model.LoginData;
import com.conferences.service.abstraction.IAuthService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *     Responds to home page requests
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public class IndexCommand extends FrontCommand {

    private static final Logger LOGGER = LogManager.getLogger(IndexCommand.class);

    private IMapper<FormError, String> formErrorMapper;
    private IAuthService authenticationService;
    private IMapper<HttpServletRequest, LoginData> mapper;

    @Override
    public void init(ServletContext context, HttpServletRequest request, HttpServletResponse response, List<String> urlParams) throws IOException {
        super.init(context, request, response, urlParams);
        formErrorMapper = MapperFactory.getInstance().getFormErrorToStringMapper();
        authenticationService = ServiceFactory.getInstance().getAuthenticationService();
        mapper = MapperFactory.getInstance().getRequestToLoginDataMapper();
    }

    /**
     * <p>
     *     Forwards to profile page if user is authorized, to login page otherwise
     * </p>
     * @throws ServletException
     * @throws IOException
     */
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

    /**
     * <p>
     *     Processed login request
     * </p>
     * @throws IOException an exception which may occur during saving errors to session
     * @throws ServletException an exception which may occur during saving errors to session
     */
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
            LOGGER.info("User not found. Redirecting to login page");
            request.setAttribute("errorMessage", formErrorMapper.map(new FormError(lang, ErrorKey.INVALID_LOGIN_OR_PASSWORD)));

            forward("login");
        }
    }
}
