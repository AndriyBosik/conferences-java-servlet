package handler;

import com.conferences.config.SortOrder;
import com.conferences.handler.implementation.MeetingUsersQueryBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

public class MeetingUsersQueryBuilderTest {

    @Test
    public void shouldAddUsersCountColumnToOrderByClause() {
        MeetingUsersQueryBuilder queryBuilder = new MeetingUsersQueryBuilder(SortOrder.ASC, "users_count");
        String query = queryBuilder.select("id")
            .orderBy("title")
            .generateQuery();
        assertEquals("SELECT id ORDER BY users_count asc,title".toLowerCase(), query.toLowerCase());
    }
}
