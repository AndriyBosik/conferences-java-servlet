package com.conferences.handler.implementation;

import com.conferences.config.SortOrder;
import com.conferences.handler.abstraction.IQueryBuilder;

import java.util.Arrays;

/**
 * {@inheritDoc}
 */
public class MeetingUsersQueryBuilder extends QueryBuilder {

    private final SortOrder sortOrder;
    private final String usersCountColumn;

    public MeetingUsersQueryBuilder(SortOrder sortOrder, String usersCountColumn) {
        super();
        this.sortOrder = sortOrder;
        this.usersCountColumn = usersCountColumn;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IQueryBuilder orderBy(String... columns) {
        String[] newColumns = Arrays.copyOf(columns, columns.length + 1);
        newColumns[0] = usersCountColumn + " " + sortOrder.toString();
        System.arraycopy(columns, 0, newColumns, 1, columns.length);
        return super.orderBy(newColumns);
    }

}
