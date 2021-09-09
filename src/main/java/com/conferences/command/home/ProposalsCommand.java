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

/**
 * <p>
 *     Responds to /home/proposals page requests
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public class ProposalsCommand extends FrontCommand {

    private IProposalDataService proposalDataService;

    @Override
    public void init(ServletContext context, HttpServletRequest request, HttpServletResponse response, List<String> urlParams) throws IOException {
        super.init(context, request, response, urlParams);
        proposalDataService = ServiceFactory.getInstance().getProposalDataService();
    }

    /**
     * <p>
     *     Adds speaker's and moderator's proposals to request and forwards it to proposals page
     * </p>
     * @throws ServletException an exception which may occur during saving errors to session
     * @throws IOException an exception which may occur during saving errors to session
     */
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
