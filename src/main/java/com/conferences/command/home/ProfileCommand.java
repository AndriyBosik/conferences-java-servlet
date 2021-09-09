package com.conferences.command.home;

import com.conferences.command.FrontCommand;
import com.conferences.config.Defaults;
import com.conferences.config.FormKeys;
import com.conferences.config.Roles;
import com.conferences.entity.User;
import com.conferences.factory.ServiceFactory;
import com.conferences.service.abstraction.ITopicProposalService;
import com.conferences.service.implementation.TopicProposalService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *     Responds to /home/profile page requests
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public class ProfileCommand extends FrontCommand {

    private ITopicProposalService topicProposalService;

    @Override
    public void init(ServletContext context, HttpServletRequest request, HttpServletResponse response, List<String> urlParams) throws IOException {
        super.init(context, request, response, urlParams);
        topicProposalService = ServiceFactory.getInstance().getTopicProposalService();
    }

    /**
     * <p>
     *     Forwards to profile partial view
     * </p>
     * @throws ServletException an exception which may occur during saving errors to session
     * @throws IOException an exception which may occur during saving errors to session
     */
    @Override
    public void process() throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute(Defaults.USER.toString());

        request.setAttribute("user", user);

        if (Roles.MODERATOR.toString().equals(user.getRole().getTitle())) {
            addModeratorDataToRequest();
        }

        extractErrorsFromSession(FormKeys.AVATAR_ERRORS);
        extractErrorsFromSession(FormKeys.PROFILE_ERRORS);
        forwardPartial("profile");
    }

    /**
     * <p>
     *     Adds available for moderator only data to request
     * </p>
     */
    private void addModeratorDataToRequest() {
        request.setAttribute("proposedTopicsCount", topicProposalService.getProposedTopicsCount());
    }
}
