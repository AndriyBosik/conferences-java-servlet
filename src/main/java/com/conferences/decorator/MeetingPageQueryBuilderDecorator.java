package com.conferences.decorator;

import com.conferences.handler.abstraction.IQueryBuilder;

/**
 * <p>
 *     Decorates {@link IQueryBuilder} to get meeting for only specific page
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public class MeetingPageQueryBuilderDecorator implements IQueryBuilder {

    protected IQueryBuilder wrapped;

    public MeetingPageQueryBuilderDecorator(IQueryBuilder wrapper) {
        this.wrapped = wrapper;
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
        wrapped.where(condition);
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
        return this;
    }

    @Override
    public IQueryBuilder orderBy(String... columns) {
        wrapped.orderBy(columns);
        return this;
    }

    @Override
    public String generateQuery() {
        return wrapped.generateQuery() +
                " OFFSET ? LIMIT ?";
    }

}
