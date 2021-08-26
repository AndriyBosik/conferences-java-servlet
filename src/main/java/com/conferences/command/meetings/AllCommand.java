package com.conferences.command.meetings;

import com.conferences.command.FrontCommand;
import com.conferences.config.HttpMethod;
import com.conferences.model.Page;
import com.conferences.service.abstraction.IMeetingService;
import com.conferences.service.implementation.MeetingService;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

public class AllCommand extends FrontCommand {

    private static final int ITEMS_COUNT = 12;

    private IMeetingService meetingService;
    private Page page;

    public AllCommand() {
        this.meetingService = new MeetingService();
        page = new Page(ITEMS_COUNT, 1);
    }

    public AllCommand(List<String> urlParams) {
        this();
        int pageNumber = Integer.parseInt(urlParams.get(0));
        page.setPageNumber(pageNumber);
    }

    @Override
    public void process() throws ServletException, IOException {
        if (request.getMethod().equals(HttpMethod.POST.toString())) {
            addMeeting();
            return;
        }

        request.setAttribute("meetings", meetingService.getAllMeetingsByPage(page));
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
