package com.conferences.command.meetings;

import com.conferences.command.FrontCommand;
import com.conferences.config.Defaults;
import com.conferences.config.FormKeys;
import com.conferences.config.Roles;
import com.conferences.entity.Meeting;
import com.conferences.entity.User;
import com.conferences.factory.ServiceFactory;
import com.conferences.model.MeetingUsersStats;
import com.conferences.service.abstraction.IMeetingService;
import com.conferences.service.abstraction.ISpeakerProposalService;
import com.conferences.service.abstraction.IUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *     Responds to /meetings/show page requests
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public class ShowCommand extends FrontCommand {

    private static final Logger LOGGER = LogManager.getLogger(ShowCommand.class);

    private int id;
    private IMeetingService meetingService;
    private IUserService userService;
    private ISpeakerProposalService speakerProposalService;

    @Override
    public void init(ServletContext context, HttpServletRequest request, HttpServletResponse response, List<String> urlParams) throws IOException {
        super.init(context, request, response, urlParams);
        try {
            LOGGER.info("Getting meeting id from url");
            id = Integer.parseInt(urlParams.get(0));
        } catch (NumberFormatException exception) {
            LOGGER.error("Bad request. Unable to parse url parameter", exception);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        meetingService = ServiceFactory.getInstance().getMeetingService();
        userService = ServiceFactory.getInstance().getUserService();
        speakerProposalService = ServiceFactory.getInstance().getSpeakerProposalService();
    }

    /**
     * <p>
     *     Gets meeting by meetingId passed as URL param and forwards it to view
     * </p>
     * @throws ServletException an exception which may occur during saving errors to session
     * @throws IOException an exception which may occur during saving errors to session
     */
    @Override
    public void process() throws ServletException, IOException {
        Meeting meeting = meetingService.getMeetingWithTopicsAndSpeakersAndUsersCount(id);
        if (meeting == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        List<User> speakers = userService.getUsersByRoleTitleWithRole("speaker");

        User user = (User) request.getSession().getAttribute(Defaults.USER.toString());
        boolean isJoined = meetingService.hasJoinedUser(id, user.getId());

        request.setAttribute("meeting", meeting);
        request.setAttribute("speakers", speakers);
        request.setAttribute("isJoined", isJoined);
        if (Roles.SPEAKER.toString().equals(user.getRole().getTitle())) {
            request.setAttribute("proposedTopicIds", speakerProposalService.getSpeakerProposedTopicIdsForMeeting(meeting.getId(), user.getId()));
        } else if (Roles.MODERATOR.toString().equals(user.getRole().getTitle())) {
            MeetingUsersStats meetingUsersStats = meetingService.getUsersWithPresenceByMeeting(id);
            request.setAttribute("stats", meetingUsersStats);
            extractErrors();
        }

        forwardPartial("meeting", meeting.getTitle());
    }

    /**
     * <p>
     *     Gets errors from session
     * </p>
     */
    private void extractErrors() {
        extractErrorsFromSession(FormKeys.PROPOSAL_ERRORS);
        extractErrorsFromSession(FormKeys.JOINING_ERRORS);
        extractErrorsFromSession(FormKeys.UPDATED_TOPIC_ERRORS);
        extractErrorsFromSession(FormKeys.CREATED_TOPIC_ERRORS);
        extractErrorsFromSession(FormKeys.SPEAKER_PROPOSAL_ERRORS);
        extractErrorsFromSession(FormKeys.TOPIC_PROPOSAL_ERRORS);
    }
}
