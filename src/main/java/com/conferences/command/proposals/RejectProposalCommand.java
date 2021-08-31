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
        int speakerId = Integer.parseInt(request.getParameter("speaker_id"));
        int topicId = Integer.parseInt(request.getParameter("topic_id"));

        if (!validSpeaker(speakerId)) {
            // TODO(speaker tries to reject proposal of another speaker)
            return;
        }

        ModeratorProposal moderatorProposal = new ModeratorProposal();
        moderatorProposal.setReportTopicId(topicId);
        moderatorProposal.setSpeakerId(speakerId);

        if (moderatorProposalService.deleteModeratorProposal(moderatorProposal)) {
            redirect(Pages.SPEAKER_PROPOSALS_PAGE.toString());
        } else {
            // TODO(bad request)
        }
    }

    private boolean validSpeaker(int speakerId) {
        User speaker = (User) request.getSession().getAttribute(Defaults.USER.toString());
        return speaker.getId() == speakerId;
    }
}
