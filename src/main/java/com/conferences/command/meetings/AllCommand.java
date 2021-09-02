package com.conferences.command.meetings;

import com.conferences.command.FrontCommand;
import com.conferences.config.Defaults;
import com.conferences.config.HttpMethod;
import com.conferences.config.Pages;
import com.conferences.entity.Meeting;
import com.conferences.entity.User;
import com.conferences.handler.abstraction.IFileHandler;
import com.conferences.handler.implementation.FileHandler;
import com.conferences.mapper.FormDataToMeetingMapper;
import com.conferences.mapper.IMapper;
import com.conferences.mapper.RequestToFileFormDataMapper;
import com.conferences.mapper.RequestToMeetingSorterMapper;
import com.conferences.model.FileFormData;
import com.conferences.model.MeetingSorter;
import com.conferences.model.Page;
import com.conferences.model.PageResponse;
import com.conferences.service.abstraction.IMeetingService;
import com.conferences.service.implementation.MeetingService;
import com.conferences.utils.FileUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class AllCommand extends FrontCommand {

    private static final int ITEMS_COUNT = 12;
    private static final String MEETINGS_IMAGES = "/resources/images/meetings/";

    private final IMeetingService meetingService;
    private final IFileHandler fileHandler;
    private final IMapper<HttpServletRequest, MeetingSorter> meetingSorterMapper;
    private final IMapper<Map<String, String>, Meeting> meetingMapper;
    private final IMapper<HttpServletRequest, FileFormData> fileFormDataMapper;
    private final Page page;

    public AllCommand() {
        this.meetingService = new MeetingService();
        this.fileHandler = new FileHandler();
        this.meetingSorterMapper = new RequestToMeetingSorterMapper();
        this.meetingMapper = new FormDataToMeetingMapper();
        fileFormDataMapper = new RequestToFileFormDataMapper();

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

        MeetingSorter meetingSorter = meetingSorterMapper.map(request);
        PageResponse<Meeting> meetingsPage = meetingService.getAllMeetingsByPageAndSorterWithUsersCountAndTopicsCount(page, meetingSorter);

        initPageFilters(meetingSorter);
        request.setAttribute("meetings", meetingsPage.getItems());
        request.setAttribute("currentPage", page.getPageNumber());
        request.setAttribute("pagesLinks", getLinkToMeetingsPages(meetingsPage.getPagesCount()));
        forwardPartial("meetings_list");
    }

    private void addMeeting() throws IOException {
        FileFormData data = fileFormDataMapper.map(request);
        if (data != null) {
            Meeting meeting = meetingMapper.map(data.getFormData());
            String imageExtension = "." + FileUtil.getFileExtension(meeting.getImagePath());
            meeting.setImagePath(generateMeetingImagePath() + imageExtension);

            if (meetingService.saveMeeting(meeting)) {
                if (!fileHandler.saveFile(data.getFileItems(), context.getRealPath(MEETINGS_IMAGES), meeting.getImagePath())) {
                    // TODO(update meeting image_path to default image)
                }
                redirect(Pages.MEETING.getUrl() + meeting.getId());
            } else {
                // TODO(fill cookies with inputted data and redirect with error message)
            }
        } else {
            // TODO(Bad request)
        }

//        response.sendRedirect(Pages.MEETINGS_LIST.toString());
    }

    private String generateMeetingImagePath() {
        User user = (User) request.getSession().getAttribute(Defaults.USER.toString());
        String filename = Defaults.MEETING_IMAGE_PREFIX + "_" + LocalDateTime.now() + "_" + user.getId();
        return FileUtil.removeFileForbiddenSymbols(filename);
    }

    private List<String> getLinkToMeetingsPages(int pagesCount) {
        List<String> links = new ArrayList<>();
        for (int i = 1; i <= pagesCount; i++) {
            links.add(Pages.MEETINGS_LIST + "/" + i);
        }
        return links;
    }

    private void initPageFilters(MeetingSorter sorter) {
        request.setAttribute("sortByOption", sorter.getSortOption().toString().toLowerCase());
        request.setAttribute("sortOrderOption", sorter.getSortOrder().toString().toLowerCase());
        request.setAttribute("filterSelector", sorter.getFilterSelector().toString().toLowerCase());
    }
}
