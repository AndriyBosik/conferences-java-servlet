package com.conferences.command.topics;

import com.conferences.command.FrontCommand;
import com.conferences.factory.ServiceFactory;
import com.conferences.service.abstraction.ITopicProposalService;
import com.conferences.service.implementation.TopicProposalService;

import javax.servlet.ServletException;
import java.io.IOException;

public class AcceptProposedCommand extends FrontCommand {

    private final ITopicProposalService topicProposalService;

    public AcceptProposedCommand() {
        topicProposalService = ServiceFactory.getInstance().getTopicProposalService();
    }

    @Override
    public void process() throws ServletException, IOException {
        int topicProposalId = Integer.parseInt(request.getParameter("id"));

        if (topicProposalService.acceptTopicProposal(topicProposalId)) {
            redirectBack();
        } else {
            // TODO(Bad request or something wrong with DB)
        }
    }
}
