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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * <p>
 *     Responds to /meetings/all/{pageNumber} page requests
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public class AllCommand extends FrontCommand {

    private static final Logger LOGGER = LogManager.getLogger(AllCommand.class);
    private static final int ITEMS_COUNT = 12;

    private IMeetingService meetingService;
    private IMapper<HttpServletRequest, MeetingSorter> meetingSorterMapper;
    private com.conferences.model.Page page;

    @Override
    public void init(ServletContext context, HttpServletRequest request, HttpServletResponse response, List<String> urlParams) throws IOException {
        super.init(context, request, response, urlParams);
        this.meetingService = ServiceFactory.getInstance().getMeetingService();
        this.meetingSorterMapper = MapperFactory.getInstance().getRequestToMeetingSorterMapper();
        int pageNumber = 1;
        if (!urlParams.isEmpty()) {
            try {
                LOGGER.info("Getting page number from url");
                pageNumber = Integer.parseInt(urlParams.get(0));
            } catch (NumberFormatException exception) {
                LOGGER.error("Unable to get page number", exception);
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        }
        this.page = new com.conferences.model.Page(ITEMS_COUNT, pageNumber);
    }

    /**
     * <p>
     *     Forwards meetings for current page to partial view
     * </p>
     * @throws ServletException an exception which may occur during saving errors to session
     * @throws IOException an exception which may occur during saving errors to session
     */
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
