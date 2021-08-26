package com.conferences.command.meetings;

import com.conferences.command.FrontCommand;
import com.conferences.entity.Meeting;
import com.conferences.service.abstraction.IMeetingService;
import com.conferences.service.implementation.MeetingService;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

public class ShowCommand extends FrontCommand {

    private int id;
    private IMeetingService meetingService;

    public ShowCommand(List<String> urlParams) {
        id = Integer.parseInt(urlParams.get(0));
        meetingService = new MeetingService();
    }

    @Override
    public void process() throws ServletException, IOException {
        Meeting meeting = meetingService.getMeetingWithTopicsAndSpeakers(id);
        request.setAttribute("meeting", meeting);

        forwardPartial("meeting");
    }
}
