package com.conferences.filter;

import com.conferences.config.HttpMethod;
import com.conferences.config.Pages;
import com.conferences.filter.model.ServletData;
import com.conferences.filter.model.UrlData;
import com.conferences.handler.PermissionsHandler;
import com.conferences.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AuthenticationFilter extends UrlDividerFilter {

    private static final String GUEST_USER = "guest";

    private PermissionsHandler permissionsHandler;

    @Override
    public void init(FilterConfig filterConfig) {
        PermissionsHandler.Builder builder = new PermissionsHandler.Builder();
        permissionsHandler = builder
                .init()
                .controlUrls(Pages.HOME.toString(), Pages.LOGOUT.toString())
                    .withMethods(HttpMethod.GET, HttpMethod.POST)
                        .allowAllRoles()
                .controlUrls(
                    Pages.PROFILE.toString(),
                    Pages.MEETINGS_LIST.toString(),
                    Pages.MEETINGS_LIST_PAGE.toString(),
                    Pages.MEETING.toString(),
                    Pages.TOPIC.toString())
                        .withMethods(HttpMethod.GET)
                            .allowAnyRoleOf("moderator", "speaker", "user")
                .controlUrls(Pages.MEETINGS_LIST.toString(), Pages.CREATE_TOPIC.toString(), Pages.UPDATE_TOPIC.toString())
                    .withMethods(HttpMethod.POST)
                        .allowAnyRoleOf("moderator")
                .controlUrls(Pages.JOIN_USER_TO_MEETING.toString())
                    .withMethods(HttpMethod.POST)
                        .allowAnyRoleOf("user")
                .build();
    }

    @Override
    public void handleFilter(ServletData servletData, UrlData urlData, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletData.getServletRequest();
        User user = (User) request.getSession().getAttribute("user");
        String url = urlData.getPath();
        String httpMethod = request.getMethod();
        String userRole = GUEST_USER;
        if (user != null && user.getRole() != null) {
            userRole = user.getRole().getTitle();
        }

        if (permissionsHandler.isAllowed(userRole, HttpMethod.fromString(httpMethod), url)) {
            filterChain.doFilter(servletData.getServletRequest(), servletData.getServletResponse());
            return;
        }

        request.getRequestDispatcher("/WEB-INF/jsp/errors/401.jsp").forward(servletData.getServletRequest(), servletData.getServletResponse());
    }

}
