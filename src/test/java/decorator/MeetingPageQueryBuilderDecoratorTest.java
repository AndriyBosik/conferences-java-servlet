package decorator;

import com.conferences.decorator.MeetingPageQueryBuilderDecorator;
import com.conferences.handler.implementation.QueryBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class MeetingPageQueryBuilderDecoratorTest {

    private static final String PAGE_SQL = "SELECT * FROM meeting LEFT JOIN report_topics rt ON rt.meeting_id=id WHERE (id>5) and (id<100) GROUP BY title ORDER BY title OFFSET ? LIMIT ?";

    private static MeetingPageQueryBuilderDecorator queryBuilder;

    @BeforeClass
    public static void beforeTest() {
        queryBuilder = new MeetingPageQueryBuilderDecorator(new QueryBuilder());
    }

    @Test
    public void shouldGenerateValidPageSqlQuery() {
        String sql = queryBuilder
            .select("*")
            .from("meeting")
            .leftJoin("report_topics rt", "rt.meeting_id=id")
            .where(queryBuilder.and("id>5", "id<100"))
            .groupBy("title")
            .orderBy("title")
            .generateQuery();
        assertEquals(PAGE_SQL.toLowerCase(), sql.toLowerCase());
    }

}
