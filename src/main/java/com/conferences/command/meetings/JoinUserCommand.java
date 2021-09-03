package com.conferences.command.meetings;

import com.conferences.command.FrontCommand;
import com.conferences.config.Defaults;
import com.conferences.config.Page;
import com.conferences.entity.Meeting;
import com.conferences.entity.User;
import com.conferences.factory.ServiceFactory;
import com.conferences.service.abstraction.IMeetingService;

import javax.servlet.ServletException;
import java.io.IOException;
import java.time.LocalDateTime;

public class JoinUserCommand extends FrontCommand {

    private final IMeetingService meetingService;

    public JoinUserCommand() {
        meetingService = ServiceFactory.getInstance().getMeetingService();
    }

    @Override
    public void process() throws ServletException, IOException {
        int meetingId = Integer.parseInt(request.getParameter("meeting_id"));
        Meeting meeting = meetingService.getMeetingById(meetingId);

        if (meeting.getDate().isBefore(LocalDateTime.now())) {
            // TODO
            redirect(Page.MEETING.getUrl() + meetingId);
            return;
        }

        User user = (User) request.getSession().getAttribute(Defaults.USER.toString());
        if (meetingService.joinUser(meetingId, user.getId())) {
            redirect(Page.MEETING.getUrl() + meetingId);
        } else {
            // TODO
        }
    }
}
