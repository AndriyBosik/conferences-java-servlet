package com.conferences.factory;

import com.conferences.config.MeetingFilterSelector;
import com.conferences.decorator.MeetingFutureSelectorQueryBuilderDecorator;
import com.conferences.decorator.MeetingPastSelectorQueryBuilderDecorator;
import com.conferences.handler.abstraction.IQueryBuilder;

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
        }
        return queryBuilder;
    }
}
