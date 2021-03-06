package com.conferences.command.topics;

import com.conferences.command.FrontCommand;
import com.conferences.config.ErrorKey;
import com.conferences.config.FormKeys;
import com.conferences.factory.ServiceFactory;
import com.conferences.model.FormError;
import com.conferences.service.abstraction.IProposedTopicDataService;
import com.conferences.service.abstraction.ITopicProposalService;
import com.conferences.service.implementation.TopicProposalService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *     Responds to /topics/reject-proposed page requests
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public class RejectProposedCommand extends FrontCommand {

    private ITopicProposalService topicProposalService;

    @Override
    public void init(ServletContext context, HttpServletRequest request, HttpServletResponse response, List<String> urlParams) throws IOException {
        super.init(context, request, response, urlParams);
        topicProposalService = ServiceFactory.getInstance().getTopicProposalService();
    }

    /**
     * <p>
     *     Rejects moderator proposal
     * </p>
     * @throws ServletException an exception which may occur during saving errors to session
     * @throws IOException an exception which may occur during saving errors to session
     */
    @Override
    public void process() throws ServletException, IOException {
        int topicProposalId = Integer.parseInt(request.getParameter("id"));

        if (!topicProposalService.rejectTopicProposal(topicProposalId)) {
            List<FormError> errors = new ArrayList<>();
            errors.add(new FormError(ErrorKey.REJECT_PROPOSAL_ERROR));
            saveErrorsToSession(FormKeys.PROPOSAL_REJECTION_ERRORS, errors);
        }
        redirectBack();
    }
}
