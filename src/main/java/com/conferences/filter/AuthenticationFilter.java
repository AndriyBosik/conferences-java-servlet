package com.conferences.filter;

import com.conferences.config.*;
import com.conferences.model.ServletData;
import com.conferences.model.UrlData;
import com.conferences.handler.implementation.PermissionsHandler;
import com.conferences.entity.User;
import com.conferences.handler.abstraction.IPermissionsHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 *     Filters access to resources
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public class AuthenticationFilter extends UrlDividerFilter {

    private static final Logger LOGGER = LogManager.getLogger(AuthenticationFilter.class);
    private static final String GUEST_USER = "guest";

    private IPermissionsHandler permissionsHandler;

    /**
     * {@inheritDoc}
     * <p>
     *     Configures {@link PermissionsHandler}
     * </p>
     */
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

    /**
     * <p>
     *    Forwards user to error page if access is denied
     * </p>
     * @param servletData data about request, response and context
     * @param urlData data about URL
     * @param filterChain {@link FilterChain}
     * @throws ServletException exception while may occur during filter processing
     * @throws IOException exception while may occur during filter processing
     */
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

        int errorCode = permissionsHandler.checkPermission(url, HttpMethod.fromString(httpMethod), userRole);
        if (errorCode == HttpServletResponse.SC_OK) {
            filterChain.doFilter(servletData.getServletRequest(), servletData.getServletResponse());
            LOGGER.info("Filtering request path {} with method {} for user {}: {}", url, httpMethod, userRole, "FORBIDDEN");
            return;
        }

        LOGGER.info("Filtering request path {} with method {} for user {}: {}", url, httpMethod, userRole, "ALLOWED");
        HttpServletResponse response = (HttpServletResponse) servletData.getServletResponse();
        response.sendError(errorCode);
    }

}
