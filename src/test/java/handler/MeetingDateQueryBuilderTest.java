package handler;

import com.conferences.config.SortOrder;
import com.conferences.handler.implementation.MeetingDateQueryBuilder;
import com.conferences.handler.implementation.MeetingTopicsQueryBuilder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MeetingDateQueryBuilderTest {

    @Test
    public void shouldAddTopicsCountColumnToOrderByClause() {
        MeetingDateQueryBuilder queryBuilder = new MeetingDateQueryBuilder(SortOrder.DESC, "m_");
        String query = queryBuilder
                .select("id")
                .orderBy("address")
                .generateQuery();

        assertEquals("SELECT id ORDER BY m_date DESC,address".toLowerCase(), query.toLowerCase());
    }
}
