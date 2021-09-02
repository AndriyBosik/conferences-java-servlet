package com.conferences.command.meetings;

import com.conferences.command.FrontCommand;
import com.conferences.config.Defaults;
import com.conferences.config.Pages;
import com.conferences.entity.Meeting;
import com.conferences.entity.User;
import com.conferences.service.abstraction.IMeetingService;
import com.conferences.service.implementation.MeetingService;

import javax.servlet.ServletException;
import java.io.IOException;
import java.time.LocalDateTime;

public class JoinUserCommand extends FrontCommand {

    private final IMeetingService meetingService;

    public JoinUserCommand() {
        meetingService = new MeetingService();
    }

    @Override
    public void process() throws ServletException, IOException {
        int meetingId = Integer.parseInt(request.getParameter("meeting_id"));
        Meeting meeting = meetingService.getMeetingById(meetingId);

        if (meeting.getDate().isBefore(LocalDateTime.now())) {
            // TODO
            redirect(Pages.MEETING.getUrl() + meetingId);
            return;
        }

        User user = (User) request.getSession().getAttribute(Defaults.USER.toString());
        if (meetingService.joinUser(meetingId, user.getId())) {
            redirect(Pages.MEETING.getUrl() + meetingId);
        } else {
            // TODO
        }
    }
}
