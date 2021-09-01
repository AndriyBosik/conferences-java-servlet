package com.conferences.command.topics;

import com.conferences.command.FrontCommand;
import com.conferences.entity.TopicProposal;
import com.conferences.mapper.IMapper;
import com.conferences.mapper.RequestToTopicProposalMapper;
import com.conferences.service.abstraction.ITopicProposalService;
import com.conferences.service.implementation.TopicProposalService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class ProposeCommand extends FrontCommand {

    private final ITopicProposalService topicProposalService;
    private final IMapper<HttpServletRequest, TopicProposal> mapper;

    public ProposeCommand() {
        topicProposalService = new TopicProposalService();
        mapper = new RequestToTopicProposalMapper();

    }

    @Override
    public void process() throws ServletException, IOException {
        TopicProposal topicProposal = mapper.map(request);

        if (topicProposalService.addTopicProposal(topicProposal)) {
            redirectBack();
        } else {
            // TODO(Not valid meeting)
        }
    }
}
