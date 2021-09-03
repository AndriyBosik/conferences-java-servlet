package com.conferences.command.home;

import com.conferences.command.FrontCommand;
import com.conferences.config.Defaults;
import com.conferences.entity.User;
import com.conferences.factory.ServiceFactory;
import com.conferences.service.abstraction.IProposalDataService;
import com.conferences.service.implementation.ProposalDataService;

import javax.servlet.ServletException;
import java.io.IOException;

public class ProposalsCommand extends FrontCommand {

    private final IProposalDataService proposalDataService;

    public ProposalsCommand() {
        proposalDataService = ServiceFactory.getInstance().getProposalDataService();
    }

    @Override
    public void process() throws ServletException, IOException {
        User speaker = (User) request.getSession().getAttribute(Defaults.USER.toString());
        request.setAttribute("moderatorProposals", proposalDataService.getActiveModeratorProposalsForSpeakerOrderByMeeting(speaker.getId()));
        request.setAttribute("speakerProposals", proposalDataService.getActiveSpeakerProposalsOrderByMeeting(speaker.getId()));

        forwardPartial("proposals");
    }
}
