package com.conferences.mapper;

import com.conferences.config.MeetingSortOption;
import com.conferences.config.MeetingFilterSelector;
import com.conferences.config.SortOrder;
import com.conferences.model.MeetingSorter;

import javax.servlet.http.HttpServletRequest;

/**
 * {@inheritDoc}
 */
public class RequestToMeetingSorterMapper implements IMapper<HttpServletRequest, MeetingSorter> {

    /**
     * <p>
     *     Maps {@link HttpServletRequest} to {@link MeetingSorter}
     * </p>
     */
    @Override
    public MeetingSorter map(HttpServletRequest request) {
        MeetingSorter meetingSorter = new MeetingSorter();
        String sortBy = request.getParameter("sort-by");
        String sortOrder = request.getParameter("sort-order");
        String filterSelector = request.getParameter("filter-selector");
        if (sortBy != null) {
            meetingSorter.setSortOption(MeetingSortOption.fromString(request.getParameter("sort-by")));
        } else {
            meetingSorter.setSortOption(MeetingSortOption.NOTHING);
        }
        if (sortOrder != null) {
            meetingSorter.setSortOrder(SortOrder.fromString(sortOrder));
        } else {
            meetingSorter.setSortOrder(SortOrder.ASC);
        }
        if (filterSelector != null) {
            meetingSorter.setFilterSelector(MeetingFilterSelector.fromString(filterSelector));
        } else {
            meetingSorter.setFilterSelector(MeetingFilterSelector.ALL);
        }
        return meetingSorter;
    }
}
