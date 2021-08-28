package com.conferences.command.meetings;

import com.conferences.command.FrontCommand;
import com.conferences.config.Defaults;
import com.conferences.config.Pages;
import com.conferences.entity.User;
import com.conferences.service.abstraction.IMeetingService;
import com.conferences.service.implementation.MeetingService;

import javax.servlet.ServletException;
import java.io.IOException;

public class JoinUserCommand extends FrontCommand {

    private IMeetingService meetingService;

    public JoinUserCommand() {
        meetingService = new MeetingService();
    }

    @Override
    public void process() throws ServletException, IOException {
        int meetingId = Integer.parseInt(request.getParameter("meeting_id"));
        User user = (User) request.getSession().getAttribute(Defaults.USER.toString());

        if (meetingService.joinUser(meetingId, user.getId())) {
            redirect(Pages.MEETING.getUrl() + meetingId);
        } else {
            // TODO
        }
    }
}
