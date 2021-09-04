package com.conferences.command.home;

import com.conferences.command.FrontCommand;
import com.conferences.config.Defaults;
import com.conferences.config.Page;
import com.conferences.entity.Meeting;
import com.conferences.entity.User;
import com.conferences.factory.MapperFactory;
import com.conferences.factory.ServiceFactory;
import com.conferences.mapper.IMapper;
import com.conferences.model.MeetingSorter;
import com.conferences.model.PageResponse;
import com.conferences.service.abstraction.IMeetingService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpeakerMeetingsCommand extends FrontCommand {

    private static final int ITEMS_COUNT = 12;

    private com.conferences.model.Page page;
    private IMeetingService meetingService;
    private IMapper<HttpServletRequest, MeetingSorter> mapper;

    @Override
    public void init(ServletContext context, HttpServletRequest request, HttpServletResponse response, List<String> urlParams) {
        super.init(context, request, response, urlParams);
        meetingService = ServiceFactory.getInstance().getMeetingService();
        mapper = MapperFactory.getInstance().getRequestToMeetingSorterMapper();
        int pageNumber = 1;
        if (!urlParams.isEmpty()) {
            pageNumber = Integer.parseInt(urlParams.get(0));
        }
        page = new com.conferences.model.Page(ITEMS_COUNT, pageNumber);
    }

    @Override
    public void process() throws ServletException, IOException {
        User speaker = (User) request.getSession().getAttribute(Defaults.USER.toString());

        MeetingSorter sorter = mapper.map(request);
        PageResponse<Meeting> meetingsPage = meetingService.getSpeakerMeetings(page, sorter, speaker.getId());

        request.setAttribute("meetings", meetingsPage.getItems());
        request.setAttribute("currentPage", page.getPageNumber());
        request.setAttribute("pagesLinks", getLinkToMeetingsPages(meetingsPage.getPagesCount()));

        forwardPartial("speaker_meetings");
    }

    private List<String> getLinkToMeetingsPages(int pagesCount) {
        List<String> links = new ArrayList<>();
        for (int i = 1; i <= pagesCount; i++) {
            links.add(Page.SPEAKER_MEETINGS_DEFAULT_PAGE + "/" + i);
        }
        return links;
    }
}
