package com.conferences.decorator;

import com.conferences.handler.abstraction.IQueryBuilder;

public class MeetingPastSelectorQueryBuilderDecorator implements IQueryBuilder {

    private final IQueryBuilder wrapped;
    private final String meetingsTablePrefix;

    public MeetingPastSelectorQueryBuilderDecorator(IQueryBuilder wrapped) {
        this(wrapped, "meetings.");
    }

    public MeetingPastSelectorQueryBuilderDecorator(IQueryBuilder wrapped, String meetingsTablePrefix) {
        this.wrapped = wrapped;
        this.meetingsTablePrefix = meetingsTablePrefix;
    }

    @Override
    public IQueryBuilder select(String... columns) {
        wrapped.select(columns);
        return this;
    }

    @Override
    public IQueryBuilder from(String... tables) {
        wrapped.from(tables);
        return this;
    }

    @Override
    public IQueryBuilder where(String condition) {
        wrapped.where(and(meetingsTablePrefix + "date < current_timestamp"));
        return this;
    }

    @Override
    public String and(String... conditions) {
        return wrapped.and(conditions);
    }

    @Override
    public IQueryBuilder leftJoin(String tableName, String condition) {
        wrapped.leftJoin(tableName, condition);
        return this;
    }

    @Override
    public IQueryBuilder groupBy(String... columns) {
        wrapped.groupBy(columns);
        return wrapped;
    }

    @Override
    public IQueryBuilder orderBy(String... columns) {
        wrapped.orderBy(columns);
        return wrapped;
    }

    @Override
    public String generateQuery() {
        return wrapped.generateQuery();
    }

}
