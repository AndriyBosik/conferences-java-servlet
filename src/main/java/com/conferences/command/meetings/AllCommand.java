package com.conferences.command.meetings;

import com.conferences.command.FrontCommand;
import com.conferences.config.FormKeys;
import com.conferences.config.Page;
import com.conferences.entity.Meeting;
import com.conferences.factory.MapperFactory;
import com.conferences.factory.ServiceFactory;
import com.conferences.mapper.IMapper;
import com.conferences.model.MeetingSorter;
import com.conferences.model.PageResponse;
import com.conferences.service.abstraction.IMeetingService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

public class AllCommand extends FrontCommand {

    private static final int ITEMS_COUNT = 12;

    private final IMeetingService meetingService;
    private final IMapper<HttpServletRequest, MeetingSorter> meetingSorterMapper;

    private final com.conferences.model.Page page;

    public AllCommand() {
        this.meetingService = ServiceFactory.getInstance().getMeetingService();
        this.meetingSorterMapper = MapperFactory.getInstance().getRequestToMeetingSorterMapper();

        this.page = new com.conferences.model.Page(ITEMS_COUNT, 1);
    }

    public AllCommand(List<String> urlParams) {
        this();
        int pageNumber = Integer.parseInt(urlParams.get(0));
        page.setPageNumber(pageNumber);
    }

    @Override
    public void process() throws ServletException, IOException {
        MeetingSorter meetingSorter = meetingSorterMapper.map(request);
        PageResponse<Meeting> meetingsPage = meetingService.getAllMeetingsByPageAndSorterWithUsersCountAndTopicsCount(page, meetingSorter);

        initPageFilters(meetingSorter);

        request.setAttribute("meetings", meetingsPage.getItems());
        request.setAttribute("currentPage", page.getPageNumber());
        request.setAttribute("pagesLinks", getLinkToMeetingsPages(meetingsPage.getPagesCount()));
        extractErrorsFromSession(FormKeys.MEETING_ERRORS);
        extractFieldValuesFromSession(FormKeys.MEETING_FIELDS);
        extractFieldValuesFromSession(FormKeys.UPDATED_MEETING_FIELDS);
        forwardPartial("meetings_list");
    }

    private List<String> getLinkToMeetingsPages(int pagesCount) {
        List<String> links = new ArrayList<>();
        for (int i = 1; i <= pagesCount; i++) {
            links.add(Page.MEETINGS_LIST + "/" + i);
        }
        return links;
    }

    private void initPageFilters(MeetingSorter sorter) {
        request.setAttribute("sortByOption", sorter.getSortOption().toString().toLowerCase());
        request.setAttribute("sortOrderOption", sorter.getSortOrder().toString().toLowerCase());
        request.setAttribute("filterSelector", sorter.getFilterSelector().toString().toLowerCase());
    }
}
