package com.conferences.command.home;

import com.conferences.command.FrontCommand;
import com.conferences.config.Defaults;
import com.conferences.config.MeetingFilterSelector;
import com.conferences.config.Pages;
import com.conferences.entity.Meeting;
import com.conferences.entity.User;
import com.conferences.handler.implementation.FileHandler;
import com.conferences.mapper.FormDataToMeetingMapper;
import com.conferences.mapper.IMapper;
import com.conferences.mapper.RequestToMeetingSorterMapper;
import com.conferences.model.MeetingSorter;
import com.conferences.model.Page;
import com.conferences.model.PageResponse;
import com.conferences.service.abstraction.IMeetingService;
import com.conferences.service.implementation.MeetingService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpeakerMeetingsCommand extends FrontCommand {

    private static final int ITEMS_COUNT = 12;

    private final Page page;
    private final IMeetingService meetingService;
    private final IMapper<HttpServletRequest, MeetingSorter> mapper;

    public SpeakerMeetingsCommand() {
        this.page = new Page(ITEMS_COUNT, 1);
        meetingService = new MeetingService();
        mapper = new RequestToMeetingSorterMapper();
    }

    public SpeakerMeetingsCommand(List<String> urlParams) {
        this();
        int pageNumber = Integer.parseInt(urlParams.get(0));
        page.setPageNumber(pageNumber);
    }

    @Override
    public void process() throws ServletException, IOException {
        User speaker = (User) request.getSession().getAttribute(Defaults.USER.toString());

        MeetingSorter sorter = mapper.map(request);
        if (sorter.getFilterSelector() == MeetingFilterSelector.ALL) {
            sorter.setFilterSelector(MeetingFilterSelector.FUTURE);
        }
        PageResponse<Meeting> meetingsPage = meetingService.getSpeakerMeetings(page, sorter, speaker.getId());

        request.setAttribute("meetings", meetingsPage.getItems());
        request.setAttribute("currentPage", page.getPageNumber());
        request.setAttribute("pagesLinks", getLinkToMeetingsPages(meetingsPage.getPagesCount()));

        forwardPartial("speaker_meetings");
    }

    private List<String> getLinkToMeetingsPages(int pagesCount) {
        List<String> links = new ArrayList<>();
        for (int i = 1; i <= pagesCount; i++) {
            links.add(Pages.SPEAKER_MEETINGS_DEFAULT_PAGE.toString() + "/" + i);
        }
        return links;
    }
}
