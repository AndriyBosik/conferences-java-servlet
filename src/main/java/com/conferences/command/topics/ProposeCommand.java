package com.conferences.command.topics;

import com.conferences.command.FrontCommand;
import com.conferences.config.FormKeys;
import com.conferences.entity.TopicProposal;
import com.conferences.factory.MapperFactory;
import com.conferences.factory.ServiceFactory;
import com.conferences.mapper.IMapper;
import com.conferences.mapper.RequestToTopicProposalMapper;
import com.conferences.model.FormError;
import com.conferences.service.abstraction.ITopicProposalService;
import com.conferences.service.implementation.TopicProposalService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public class ProposeCommand extends FrontCommand {

    private final ITopicProposalService topicProposalService;
    private final IMapper<HttpServletRequest, TopicProposal> mapper;

    public ProposeCommand() {
        topicProposalService = ServiceFactory.getInstance().getTopicProposalService();
        mapper = MapperFactory.getInstance().getRequestToTopicProposalMapper();

    }

    @Override
    public void process() throws ServletException, IOException {
        TopicProposal topicProposal = mapper.map(request);

        List<FormError> errors = topicProposalService.addTopicProposal(topicProposal);
        if (!errors.isEmpty()) {
            saveErrorsToSession(FormKeys.TOPIC_PROPOSAL_ERRORS, errors);
        }
        redirectBack();
    }
}
