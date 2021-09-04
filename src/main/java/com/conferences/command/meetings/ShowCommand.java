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

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ShowCommand extends FrontCommand {

    private int id;
    private IMeetingService meetingService;
    private IUserService userService;
    private ISpeakerProposalService speakerProposalService;

    @Override
    public void init(ServletContext context, HttpServletRequest request, HttpServletResponse response, List<String> urlParams) {
        super.init(context, request, response, urlParams);
        id = Integer.parseInt(urlParams.get(0));
        meetingService = ServiceFactory.getInstance().getMeetingService();
        userService = ServiceFactory.getInstance().getUserService();
        speakerProposalService = ServiceFactory.getInstance().getSpeakerProposalService();
    }

    @Override
    public void process() throws ServletException, IOException {
        Meeting meeting = meetingService.getMeetingWithTopicsAndSpeakersAndUsersCount(id);

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

    private void extractErrors() {
        extractErrorsFromSession(FormKeys.PROPOSAL_ERRORS);
        extractErrorsFromSession(FormKeys.JOINING_ERRORS);
        extractErrorsFromSession(FormKeys.UPDATED_TOPIC_ERRORS);
        extractErrorsFromSession(FormKeys.CREATED_TOPIC_ERRORS);
        extractErrorsFromSession(FormKeys.SPEAKER_PROPOSAL_ERRORS);
        extractErrorsFromSession(FormKeys.TOPIC_PROPOSAL_ERRORS);
    }
}
