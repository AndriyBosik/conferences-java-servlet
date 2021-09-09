package com.conferences.command.topics;

import com.conferences.command.FrontCommand;
import com.conferences.config.FormKeys;
import com.conferences.entity.TopicProposal;
import com.conferences.factory.MapperFactory;
import com.conferences.factory.ServiceFactory;
import com.conferences.mapper.IMapper;
import com.conferences.model.FormError;
import com.conferences.service.abstraction.ITopicProposalService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *     Responds to /topics/propose page requests
 * </p>
 */
public class ProposeCommand extends FrontCommand {

    private ITopicProposalService topicProposalService;
    private IMapper<HttpServletRequest, TopicProposal> mapper;

    @Override
    public void init(ServletContext context, HttpServletRequest request, HttpServletResponse response, List<String> urlParams) throws IOException {
        super.init(context, request, response, urlParams);
        topicProposalService = ServiceFactory.getInstance().getTopicProposalService();
        mapper = MapperFactory.getInstance().getRequestToTopicProposalMapper();
    }

    /**
     * <p>
     *     Proposes topic for meeting
     * </p>
     * @throws ServletException an exception which may occur during saving errors to session
     * @throws IOException an exception which may occur during saving errors to session
     */
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
