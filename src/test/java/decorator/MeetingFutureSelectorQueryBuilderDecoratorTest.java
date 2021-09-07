package decorator;

import com.conferences.decorator.MeetingFutureSelectorQueryBuilderDecorator;
import com.conferences.handler.abstraction.IQueryBuilder;
import com.conferences.handler.implementation.QueryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.*;

public class MeetingFutureSelectorQueryBuilderDecoratorTest {

    private static final String SQL = "SELECT * FROM test_table LEFT JOIN test t ON t.id=test_id WHERE (date > current_timestamp) and (id>5) GROUP BY id ORDER BY id";

    private static IQueryBuilder queryBuilder;

    @BeforeClass
    public static void beforeTest() {
        queryBuilder = new MeetingFutureSelectorQueryBuilderDecorator(new QueryBuilder(), "");
    }

    @Test
    public void shouldGenerateValidSql() {
        String sql = queryBuilder
            .select("*")
            .from("test_table")
            .leftJoin("test t", "t.id=test_id")
            .where("id>5")
            .groupBy("id")
            .orderBy("id")
            .generateQuery();
        assertEquals(SQL.toLowerCase(), sql.toLowerCase());
    }

}
