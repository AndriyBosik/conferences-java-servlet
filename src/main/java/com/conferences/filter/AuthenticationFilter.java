package com.conferences.filter;

import com.conferences.config.Defaults;
import com.conferences.config.HttpMethod;
import com.conferences.config.Page;
import com.conferences.config.Roles;
import com.conferences.filter.model.ServletData;
import com.conferences.filter.model.UrlData;
import com.conferences.handler.implementation.PermissionsHandler;
import com.conferences.entity.User;
import com.conferences.handler.abstraction.IPermissionsHandler;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AuthenticationFilter extends UrlDividerFilter {

    private static final String GUEST_USER = "guest";

    private IPermissionsHandler permissionsHandler;

    @Override
    public void init(FilterConfig filterConfig) {
        PermissionsHandler.Builder builder = new PermissionsHandler.Builder();
        permissionsHandler = builder
                .init()
                .controlUrls(Page.HOME.toString(), Page.LOGOUT.toString(), Page.SIGN_UP_USER.toString())
                    .withMethods(HttpMethod.GET, HttpMethod.POST)
                        .allowAllRoles()
                .controlUrls(
                    Page.PROFILE.toString(),
                    Page.MEETINGS_LIST.toString(),
                    Page.MEETINGS_LIST_PAGE.toString(),
                    Page.MEETING.toString())
                        .withMethods(HttpMethod.GET)
                            .allowAnyRoleOf(Roles.MODERATOR.toString(), Roles.SPEAKER.toString(), Roles.USER.toString())
                .controlUrls(
                    Page.CHANGE_USER_AVATAR.toString(),
                    Page.UPDATE_USER_PROFILE.toString())
                        .withMethods(HttpMethod.POST)
                            .allowAnyRoleOf(Roles.MODERATOR.toString(), Roles.SPEAKER.toString(), Roles.USER.toString())
                .controlUrls(
                    Page.MEETINGS_CREATE.toString(),
                    Page.CREATE_TOPIC.toString(),
                    Page.UPDATE_TOPIC.toString(),
                    Page.API_TOPIC_PROPOSE_FOR_USER.toString(),
                    Page.ACCEPT_PROPOSED_TOPIC.toString(),
                    Page.REJECT_PROPOSED_TOPIC.toString(),
                    Page.SAVE_USERS_PRESENCE.toString())
                        .withMethods(HttpMethod.POST)
                            .allowAnyRoleOf(Roles.MODERATOR.toString())
                .controlUrls(
                        Page.SET_SPEAKER_FROM_PROPOSALS.toString(),
                        Page.MEETING_EDIT.toString())
                    .withMethods(HttpMethod.POST)
                        .allowAnyRoleOf(Roles.MODERATOR.toString(), Roles.SPEAKER.toString())
                .controlUrls(
                    Page.API_TOPIC_PROPOSALS.toString(),
                    Page.API_TOPIC_AVAILABLE_SPEAKERS_FOR_PROPOSAL.toString(),
                    Page.PROPOSED_TOPICS.toString())
                        .withMethods(HttpMethod.GET)
                            .allowAnyRoleOf(Roles.MODERATOR.toString())
                .controlUrls(Page.JOIN_USER_TO_MEETING.toString())
                    .withMethods(HttpMethod.POST)
                        .allowAnyRoleOf(Roles.USER.toString())
                .controlUrls(
                    Page.PROPOSE_SPEAKER_TO_TOPIC.toString(),
                    Page.REJECT_PROPOSAL.toString(),
                    Page.PROPOSE_TOPIC.toString())
                        .withMethods(HttpMethod.POST)
                            .allowAnyRoleOf(Roles.SPEAKER.toString())
                .controlUrls(
                    Page.SPEAKER_PROPOSALS_PAGE.toString(),
                    Page.SPEAKER_MEETINGS_DEFAULT_PAGE.toString(),
                    Page.SPEAKER_MEETINGS.toString())
                        .allowAnyRoleOf(Roles.SPEAKER.toString())
                .build();
    }

    @Override
    public void handleFilter(ServletData servletData, UrlData urlData, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletData.getServletRequest();
        User user = (User) request.getSession().getAttribute(Defaults.USER.toString());
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
