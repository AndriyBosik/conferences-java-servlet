package com.conferences.model;

import com.conferences.config.MeetingSortOption;
import com.conferences.config.MeetingFilterSelector;
import com.conferences.config.SortOrder;

public class MeetingSorter {

    private MeetingSortOption sortOption;
    private SortOrder sortOrder;
    private MeetingFilterSelector filterSelector;

    public MeetingSortOption getSortOption() {
        return sortOption;
    }

    public void setSortOption(MeetingSortOption sortOption) {
        this.sortOption = sortOption;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    public MeetingFilterSelector getFilterSelector() {
        return filterSelector;
    }

    public void setFilterSelector(MeetingFilterSelector filterSelector) {
        this.filterSelector = filterSelector;
    }
}
