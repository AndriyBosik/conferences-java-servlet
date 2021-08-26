package com.conferences.command.meetings;

import com.conferences.command.FrontCommand;
import com.conferences.config.HttpMethod;
import com.conferences.entity.Meeting;
import com.conferences.model.Page;
import com.conferences.service.abstraction.IMeetingService;
import com.conferences.service.implementation.MeetingService;
import com.conferences.validator.IValidator;
import com.conferences.validator.MeetingValidator;

import javax.servlet.ServletException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AllCommand extends FrontCommand {

    private static final int ITEMS_COUNT = 12;

    private IMeetingService meetingService;
    private IValidator<Meeting> meetingValidator;
    private Page page;

    public AllCommand() {
        this.meetingService = new MeetingService();
        this.meetingValidator = new MeetingValidator();

        this.page = new Page(ITEMS_COUNT, 1);
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
        String strDate = request.getParameter("date") + " " + request.getParameter("hours") + ":" + request.getParameter("minutes");
        Date date = null;
        try {
            date = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(strDate);
        } catch (ParseException exception) {
            exception.printStackTrace();
        }

        Meeting meeting = new Meeting();

        meeting.setTitle(request.getParameter("title"));
        meeting.setDescription(request.getParameter("description"));
        meeting.setImagePath(request.getParameter("image-path"));
        meeting.setDate(date);

        if (meetingValidator.isValid(meeting)) {
            // redirect with success message
        } else {
            // fill cookies with inputted data and redirect with error message
        }

//        response.sendRedirect(Pages.MEETINGS_LIST.toString());
    }
}
