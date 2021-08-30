package com.conferences.factory;

import com.conferences.config.MeetingSortOption;
import com.conferences.handler.abstraction.IQueryBuilder;
import com.conferences.handler.implementation.MeetingDateQueryBuilder;
import com.conferences.handler.implementation.MeetingTopicsQueryBuilder;
import com.conferences.handler.implementation.MeetingUsersQueryBuilder;
import com.conferences.handler.implementation.QueryBuilder;
import com.conferences.model.MeetingSorter;

import java.util.Map;

public class MeetingSorterQueryBuilderFactory {

    public static final String MEETINGS_KEY = "meetings";
    public static final String TOPICS_COUNT_KEY = "topicsCount";
    public static final String USERS_COUNT_KEY = "usersCount";

    private static MeetingSorterQueryBuilderFactory instance;

    private MeetingSorterQueryBuilderFactory() {}

    public synchronized static MeetingSorterQueryBuilderFactory getInstance() {
        if (instance == null) {
            instance = new MeetingSorterQueryBuilderFactory();
        }
        return instance;
    }

    public IQueryBuilder getQueryBuilder(MeetingSorter sorter, Map<String, String> columns) {
        final MeetingSortOption sortOption = sorter.getSortOption();
        switch (sortOption) {
            case DATE:
                return new MeetingDateQueryBuilder(sorter.getSortOrder(), columns.get(MEETINGS_KEY));
            case USERS:
                return new MeetingUsersQueryBuilder(sorter.getSortOrder(), columns.get(USERS_COUNT_KEY));
            case TOPICS:
                return new MeetingTopicsQueryBuilder(sorter.getSortOrder(), columns.get(TOPICS_COUNT_KEY));
        }
        return new QueryBuilder();
    }

}
