package com.conferences.command.topics;

import com.conferences.command.FrontCommand;
import com.conferences.config.FormKeys;
import com.conferences.factory.ServiceFactory;
import com.conferences.service.abstraction.IProposedTopicDataService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *     Responds to /topics/speaker-proposed page requests
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public class SpeakerProposedCommand extends FrontCommand {

    private IProposedTopicDataService proposedTopicDataService;

    @Override
    public void init(ServletContext context, HttpServletRequest request, HttpServletResponse response, List<String> urlParams) throws IOException {
        super.init(context, request, response, urlParams);
        proposedTopicDataService = ServiceFactory.getInstance().getProposedTopicDataService();
    }

    /**
     * <p>
     *     Forwards proposed topics to view
     * </p>
     * @throws ServletException an exception which may occur during saving errors to session
     * @throws IOException an exception which may occur during saving errors to session
     */
    @Override
    public void process() throws ServletException, IOException {
        request.setAttribute("proposedTopics", proposedTopicDataService.getProposedTopicsOrderByMeeting());
        extractErrorsFromSession(FormKeys.ACCEPT_PROPOSAL_ERRORS);

        forwardPartial("proposed_topics");
    }
}
