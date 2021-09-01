package com.conferences.command.proposals;

import com.conferences.command.FrontCommand;
import com.conferences.config.Defaults;
import com.conferences.config.Pages;
import com.conferences.entity.ModeratorProposal;
import com.conferences.entity.User;
import com.conferences.service.abstraction.IModeratorProposalService;
import com.conferences.service.implementation.ModeratorProposalService;

import javax.servlet.ServletException;
import java.io.IOException;

public class RejectProposalCommand extends FrontCommand {

    private final IModeratorProposalService moderatorProposalService;

    public RejectProposalCommand() {
        moderatorProposalService = new ModeratorProposalService();
    }

    @Override
    public void process() throws ServletException, IOException {
        User speaker = (User) request.getSession().getAttribute(Defaults.USER.toString());
        int speakerId = speaker.getId();
        int topicId = Integer.parseInt(request.getParameter("topic_id"));

        ModeratorProposal moderatorProposal = new ModeratorProposal();
        moderatorProposal.setReportTopicId(topicId);
        moderatorProposal.setSpeakerId(speakerId);

        if (moderatorProposalService.deleteModeratorProposal(moderatorProposal)) {
            redirect(Pages.SPEAKER_PROPOSALS_PAGE.toString());
        } else {
            // TODO(bad request)
        }
    }
}
