package com.conferences.command.meetings;

import com.conferences.command.FrontCommand;
import com.conferences.config.Defaults;
import com.conferences.config.HttpMethod;
import com.conferences.config.Pages;
import com.conferences.entity.Meeting;
import com.conferences.handler.LinkHandler;
import com.conferences.handler.abstraction.IFileHandler;
import com.conferences.handler.implementation.FileHandler;
import com.conferences.model.Page;
import com.conferences.service.abstraction.IMeetingService;
import com.conferences.service.implementation.MeetingService;
import com.conferences.validator.IValidator;
import com.conferences.validator.MeetingValidator;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AllCommand extends FrontCommand {

    private static final int ITEMS_COUNT = 12;
    private static final String MEETINGS_IMAGES = "/resources/images/meetings/";

    private IMeetingService meetingService;
    private IFileHandler fileHandler;
    private LinkHandler linkHandler;
    private Page page;

    public AllCommand() {
        this.meetingService = new MeetingService();
        this.fileHandler = new FileHandler();
        this.linkHandler = new LinkHandler();

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
        Map<String, String> formData = fileHandler.saveFile(request, context.getRealPath(MEETINGS_IMAGES));
        if (formData != null) {
            String strDate = formData.get("date") + " " + formData.get("hours") + ":" + formData.get("minutes");
            Date date = null;
            try {
                date = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(strDate);
            } catch (ParseException exception) {
                exception.printStackTrace();
            }

            Meeting meeting = new Meeting();

            meeting.setTitle(formData.get("title"));
            meeting.setDescription(formData.get("description"));
            meeting.setImagePath(formData.get("image_path"));
            meeting.setDate(date);

            if (meetingService.saveMeeting(meeting)) {
                String lang = (String) request.getAttribute(Defaults.CURRENT_LANG.toString());
                String url = linkHandler.addLangToUrl(Pages.MEETING.getUrl() + meeting.getId(), lang);

                response.sendRedirect(url);
            } else {
                // fill cookies with inputted data and redirect with error message
            }
        } else {
            // Bad request
        }

//        response.sendRedirect(Pages.MEETINGS_LIST.toString());
    }
}
