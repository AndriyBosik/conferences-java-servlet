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

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RejectProposalCommand extends FrontCommand {

    private final IModeratorProposalService moderatorProposalService;

    public RejectProposalCommand() {
        moderatorProposalService = ServiceFactory.getInstance().getModeratorProposalService();
    }

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
