package com.conferences.command.meetings;

import com.conferences.command.FrontCommand;
import com.conferences.config.Defaults;
import com.conferences.config.Roles;
import com.conferences.entity.Meeting;
import com.conferences.entity.User;
import com.conferences.entity.UserMeeting;
import com.conferences.model.MeetingUsersStats;
import com.conferences.service.abstraction.IMeetingService;
import com.conferences.service.abstraction.ISpeakerProposalService;
import com.conferences.service.abstraction.IUserService;
import com.conferences.service.implementation.MeetingService;
import com.conferences.service.implementation.SpeakerProposalService;
import com.conferences.service.implementation.UserService;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

public class ShowCommand extends FrontCommand {

    private final int id;
    private final IMeetingService meetingService;
    private final IUserService userService;
    private final ISpeakerProposalService speakerProposalService;

    public ShowCommand(List<String> urlParams) {
        id = Integer.parseInt(urlParams.get(0));
        meetingService = new MeetingService();
        userService = new UserService();
        speakerProposalService = new SpeakerProposalService();
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
        }

        forwardPartial("meeting");
    }
}
