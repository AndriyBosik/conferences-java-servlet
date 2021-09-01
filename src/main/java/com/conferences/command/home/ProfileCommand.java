package com.conferences.command.home;

import com.conferences.command.FrontCommand;
import com.conferences.config.Roles;
import com.conferences.entity.User;
import com.conferences.service.abstraction.ITopicProposalService;
import com.conferences.service.implementation.TopicProposalService;

import javax.servlet.ServletException;
import java.io.IOException;

public class ProfileCommand extends FrontCommand {

    private final ITopicProposalService topicProposalService;

    public ProfileCommand() {
        topicProposalService = new TopicProposalService();
    }

    @Override
    public void process() throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");

        request.setAttribute("user", user);

        if (Roles.MODERATOR.toString().equals(user.getRole().getTitle())) {
            addModeratorDataToRequest();
        }

        forwardPartial("profile");
    }

    private void addModeratorDataToRequest() {
        request.setAttribute("proposedTopicsCount", topicProposalService.getProposedTopicsCount());
    }
}
