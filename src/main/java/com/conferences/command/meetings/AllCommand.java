package com.conferences.command.meetings;

import com.conferences.command.FrontCommand;
import com.conferences.service.abstraction.IMeetingService;
import com.conferences.service.implementation.MeetingService;

import javax.servlet.ServletException;
import java.io.IOException;

public class AllCommand extends FrontCommand {

    private IMeetingService meetingService;

    public AllCommand() {
        this.meetingService = new MeetingService();
    }

    @Override
    public void process() throws ServletException, IOException {
        request.setAttribute("meetings", meetingService.getAllMeetings());

        forwardPartial("meetings_list");
    }
}
