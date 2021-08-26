package com.conferences.command.meetings;

import com.conferences.command.FrontCommand;
import com.conferences.config.HttpMethod;
import com.conferences.config.Pages;
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
        if (request.getMethod().equals(HttpMethod.POST.toString())) {
            addMeeting();
            return;
        }

        request.setAttribute("meetings", meetingService.getAllMeetings());
        forwardPartial("meetings_list");
    }

    private void addMeeting() throws IOException {
        System.out.println(request.getParameter("title"));
        System.out.println(request.getParameter("description"));
        System.out.println(request.getParameter("image-path"));
        System.out.println(request.getParameter("date"));
        System.out.println(request.getParameter("hours"));
        System.out.println(request.getParameter("minutes"));

//        response.sendRedirect(Pages.MEETINGS_LIST.toString());
    }
}
