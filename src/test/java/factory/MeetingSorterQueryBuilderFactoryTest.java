package factory;

import com.conferences.config.MeetingSortOption;
import com.conferences.factory.MeetingSorterQueryBuilderFactory;
import com.conferences.handler.abstraction.IQueryBuilder;
import com.conferences.handler.implementation.MeetingDateQueryBuilder;
import com.conferences.handler.implementation.MeetingTopicsQueryBuilder;
import com.conferences.handler.implementation.MeetingUsersQueryBuilder;
import com.conferences.handler.implementation.QueryBuilder;
import com.conferences.model.MeetingSorter;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class MeetingSorterQueryBuilderFactoryTest {

    private static Map<String, String> columns;
    private static MeetingSorter sorter;

    @BeforeClass
    public static void beforeTest() {
        columns = new HashMap<>();
        columns.put("meetings", "m");
        columns.put("topicsCount", "tc");
        columns.put("usersCount", "uc");

        sorter = new MeetingSorter();
        sorter.setSortOption(MeetingSortOption.DATE);
    }

    @Test
    public void shouldReturnMeetingDateQueryBuilder() {
        sorter.setSortOption(MeetingSortOption.DATE);
        IQueryBuilder queryBuilder = MeetingSorterQueryBuilderFactory.getInstance().getQueryBuilder(sorter, columns);
        assertEquals(MeetingDateQueryBuilder.class, queryBuilder.getClass());
    }

    @Test
    public void shouldReturnMeetingUsersQueryBuilder() {
        sorter.setSortOption(MeetingSortOption.USERS);
        IQueryBuilder queryBuilder = MeetingSorterQueryBuilderFactory.getInstance().getQueryBuilder(sorter, columns);
        assertEquals(MeetingUsersQueryBuilder.class, queryBuilder.getClass());
    }

    @Test
    public void shouldReturnMeetingTopicsQueryBuilder() {
        sorter.setSortOption(MeetingSortOption.TOPICS);
        IQueryBuilder queryBuilder = MeetingSorterQueryBuilderFactory.getInstance().getQueryBuilder(sorter, columns);
        assertEquals(MeetingTopicsQueryBuilder.class, queryBuilder.getClass());
    }

    @Test
    public void shouldReturnSimpleQueryBuilder() {
        sorter.setSortOption(MeetingSortOption.NOTHING);
        IQueryBuilder queryBuilder = MeetingSorterQueryBuilderFactory.getInstance().getQueryBuilder(sorter, columns);
        assertEquals(QueryBuilder.class, queryBuilder.getClass());
    }
}
