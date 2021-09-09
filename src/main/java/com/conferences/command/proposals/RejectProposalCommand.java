package com.conferences.command.proposals;

import com.conferences.command.FrontCommand;
import com.conferences.config.Defaults;
import com.conferences.config.ErrorKey;
import com.conferences.config.FormKeys;
import com.conferences.config.Page;
import com.conferences.entity.ModeratorProposal;
import com.conferences.entity.User;
import com.conferences.factory.ServiceFactory;
import com.conferences.model.FormError;
import com.conferences.service.abstraction.IModeratorProposalService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *     Responds to /proposals/reject-proposal page requests
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public class RejectProposalCommand extends FrontCommand {

    private IModeratorProposalService moderatorProposalService;

    @Override
    public void init(ServletContext context, HttpServletRequest request, HttpServletResponse response, List<String> urlParams) throws IOException {
        super.init(context, request, response, urlParams);
        moderatorProposalService = ServiceFactory.getInstance().getModeratorProposalService();
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
        User speaker = (User) request.getSession().getAttribute(Defaults.USER.toString());
        int speakerId = speaker.getId();
        int topicId = Integer.parseInt(request.getParameter("topic_id"));

        ModeratorProposal moderatorProposal = new ModeratorProposal();
        moderatorProposal.setReportTopicId(topicId);
        moderatorProposal.setSpeakerId(speakerId);

        if (!moderatorProposalService.deleteModeratorProposal(moderatorProposal)) {
            List<FormError> errors = new ArrayList<>();
            errors.add(new FormError(ErrorKey.REJECT_PROPOSAL_ERROR));
            saveErrorsToSession(FormKeys.PROPOSAL_REJECTION_ERRORS, errors);
        }
        redirect(Page.SPEAKER_PROPOSALS_PAGE.toString());
    }
}
