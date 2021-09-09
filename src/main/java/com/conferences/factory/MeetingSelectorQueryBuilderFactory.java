package com.conferences.factory;

import com.conferences.config.MeetingFilterSelector;
import com.conferences.decorator.MeetingFutureSelectorQueryBuilderDecorator;
import com.conferences.decorator.MeetingPastSelectorQueryBuilderDecorator;
import com.conferences.handler.abstraction.IQueryBuilder;

/**
 * <p>
 *     Instantiates IQueryBuilder decorator by {@link MeetingFilterSelector}
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public class MeetingSelectorQueryBuilderFactory {

    private static MeetingSelectorQueryBuilderFactory instance;

    private MeetingSelectorQueryBuilderFactory() {}

    public static MeetingSelectorQueryBuilderFactory getInstance() {
        if (instance == null) {
            instance = new MeetingSelectorQueryBuilderFactory();
        }
        return instance;
    }

    public IQueryBuilder getDecorator(IQueryBuilder queryBuilder, MeetingFilterSelector sortSelector) {
        switch (sortSelector) {
            case PAST:
                return new MeetingPastSelectorQueryBuilderDecorator(queryBuilder);
            case FUTURE:
                return new MeetingFutureSelectorQueryBuilderDecorator(queryBuilder);
            default:
                return queryBuilder;
        }
    }
}
