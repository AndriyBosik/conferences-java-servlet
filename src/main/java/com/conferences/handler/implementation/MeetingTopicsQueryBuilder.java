package com.conferences.handler.implementation;

import com.conferences.config.SortOrder;
import com.conferences.handler.abstraction.IQueryBuilder;

import java.util.Arrays;

/**
 * {@inheritDoc}
 */
public class MeetingTopicsQueryBuilder extends QueryBuilder {

    private final SortOrder sortOrder;
    private final String topicsCountColumn;

    public MeetingTopicsQueryBuilder(SortOrder sortOrder, String topicsCountColumn) {
        super();
        this.sortOrder = sortOrder;
        this.topicsCountColumn = topicsCountColumn;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IQueryBuilder orderBy(String... columns) {
        String[] newColumns = Arrays.copyOf(columns, columns.length + 1);
        newColumns[0] = topicsCountColumn + " " + sortOrder.toString();
        System.arraycopy(columns, 0, newColumns, 1, columns.length);
        return super.orderBy(newColumns);
    }

}
