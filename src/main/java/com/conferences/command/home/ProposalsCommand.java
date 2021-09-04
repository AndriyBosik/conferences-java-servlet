package com.conferences.command.home;

import com.conferences.command.FrontCommand;
import com.conferences.config.Defaults;
import com.conferences.config.FormKeys;
import com.conferences.entity.User;
import com.conferences.factory.ServiceFactory;
import com.conferences.service.abstraction.IProposalDataService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ProposalsCommand extends FrontCommand {

    private IProposalDataService proposalDataService;

    @Override
    public void init(ServletContext context, HttpServletRequest request, HttpServletResponse response, List<String> urlParams) {
        super.init(context, request, response, urlParams);
        proposalDataService = ServiceFactory.getInstance().getProposalDataService();
    }

    @Override
    public void process() throws ServletException, IOException {
        User speaker = (User) request.getSession().getAttribute(Defaults.USER.toString());
        request.setAttribute("moderatorProposals", proposalDataService.getActiveModeratorProposalsForSpeakerOrderByMeeting(speaker.getId()));
        request.setAttribute("speakerProposals", proposalDataService.getActiveSpeakerProposalsOrderByMeeting(speaker.getId()));
        extractErrorsFromSession(FormKeys.PROPOSAL_ERRORS);
        extractErrorsFromSession(FormKeys.PROPOSAL_REJECTION_ERRORS);

        forwardPartial("proposals");
    }
}
