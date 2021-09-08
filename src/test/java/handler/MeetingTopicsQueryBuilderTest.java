package handler;

import com.conferences.config.SortOrder;
import com.conferences.handler.implementation.MeetingTopicsQueryBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

public class MeetingTopicsQueryBuilderTest {

    @Test
    public void shouldAddTopicsCountColumnToOrderByClause() {
        MeetingTopicsQueryBuilder queryBuilder = new MeetingTopicsQueryBuilder(SortOrder.DESC, "topics_count");
        String query = queryBuilder
            .select("id")
            .orderBy("description")
            .generateQuery();

        assertEquals("SELECT id ORDER BY topics_count DESC,description".toLowerCase(), query.toLowerCase());
    }
}
