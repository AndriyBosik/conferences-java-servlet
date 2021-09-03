package com.conferences.command.topics;

import com.conferences.command.FrontCommand;
import com.conferences.factory.ServiceFactory;
import com.conferences.service.abstraction.IProposedTopicDataService;
import com.conferences.service.abstraction.ITopicProposalService;
import com.conferences.service.implementation.TopicProposalService;

import javax.servlet.ServletException;
import java.io.IOException;

public class RejectProposedCommand extends FrontCommand {

    private final ITopicProposalService topicProposalService;

    public RejectProposedCommand() {
        topicProposalService = ServiceFactory.getInstance().getTopicProposalService();
    }

    @Override
    public void process() throws ServletException, IOException {
        int topicProposalId = Integer.parseInt(request.getParameter("id"));

        if (topicProposalService.rejectTopicProposal(topicProposalId)) {
            redirectBack();
        } else {
            // TODO(Error)
        }
    }
}
