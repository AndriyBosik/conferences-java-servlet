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

public class ProfileCommand extends FrontCommand {

    private ITopicProposalService topicProposalService;

    @Override
    public void init(ServletContext context, HttpServletRequest request, HttpServletResponse response, List<String> urlParams) {
        super.init(context, request, response, urlParams);
        topicProposalService = ServiceFactory.getInstance().getTopicProposalService();
    }

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

    private void addModeratorDataToRequest() {
        request.setAttribute("proposedTopicsCount", topicProposalService.getProposedTopicsCount());
    }
}
