package com.conferences.handler.implementation;

import com.conferences.config.SortOrder;
import com.conferences.handler.abstraction.IQueryBuilder;

import java.util.Arrays;

public class MeetingDateQueryBuilder extends QueryBuilder {

    private final SortOrder sortOrder;
    private final String meetingsTablePrefix;

    public MeetingDateQueryBuilder(SortOrder sortOrder, String meetingsTablePrefix) {
        super();
        this.sortOrder = sortOrder;
        this.meetingsTablePrefix = meetingsTablePrefix;
    }

    @Override
    public IQueryBuilder orderBy(String... columns) {
        String[] newColumns = Arrays.copyOf(columns, columns.length + 1);
        newColumns[0] = meetingsTablePrefix + "date " + sortOrder.toString();
        System.arraycopy(columns, 0, newColumns, 1, columns.length);
        return super.orderBy(newColumns);
    }

}
