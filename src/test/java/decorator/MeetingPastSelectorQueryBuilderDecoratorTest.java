package decorator;

import com.conferences.decorator.MeetingPastSelectorQueryBuilderDecorator;
import com.conferences.handler.implementation.QueryBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.*;

public class MeetingPastSelectorQueryBuilderDecoratorTest {

    private static final String SQL = "SELECT * FROM meetings LEFT JOIN report_topics rt ON rt.meeting_id=id WHERE (date < current_timestamp) and (id>5) GROUP BY title ORDER BY title";

    private static MeetingPastSelectorQueryBuilderDecorator queryBuilder;

    @BeforeClass
    public static void beforeTest() {
        queryBuilder = new MeetingPastSelectorQueryBuilderDecorator(new QueryBuilder(), "");
    }

    @Test
    public void shouldGenerateValidSql() {
        String sql = queryBuilder
            .select("*")
            .from("meetings")
            .leftJoin("report_topics rt", "rt.meeting_id=id")
            .where("id>5")
            .groupBy("title")
            .orderBy("title")
            .generateQuery();
        assertEquals(SQL.toLowerCase(), sql.toLowerCase());
    }
}
