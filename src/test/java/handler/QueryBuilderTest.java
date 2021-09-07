package handler;

import com.conferences.handler.abstraction.IQueryBuilder;
import com.conferences.handler.implementation.QueryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class QueryBuilderTest {

    private static final String SIMPLE_SQL = "SELECT * FROM users";
    private static final String SQL_WITH_WHERE_CLAUSE = "SELECT * FROM users WHERE id=5";
    private static final String SQL_WITH_WHERE_CLAUSE_AND = "SELECT * FROM users WHERE (id=5) AND (login='login')";
    private static final String SQL_WITH_LEFT_JOIN = "SELECT * FROM users LEFT JOIN roles r ON r.id=role_id";
    private static final String SQL_WITH_GROUP_BY = "SELECT * FROM users GROUP BY id,login,email";
    private static final String SQL_WITH_ORDER_BY = "SELECT * FROM users ORDER BY id,login,email";
    private static final String SQL_WITH_MULTIPLE_TABLES = "SELECT * FROM users,roles";
    private static final String SQL_WITH_MULTIPLE_SELECT = "SELECT users.*,roles.* FROM users,roles";

    private static IQueryBuilder queryBuilder;

    @BeforeClass
    public static void beforeTest() {
        queryBuilder = new QueryBuilder();
    }

    @Test
    public void shouldGenerateSimpleSql() {
        String sql = queryBuilder
            .select("*")
            .from("users")
            .generateQuery();
        assertEquals(SIMPLE_SQL.toLowerCase(), sql.toLowerCase());
    }

    @Test
    public void shouldGenerateSqlWithWhereClause() {
        String sql = queryBuilder
            .select("*")
            .from("users")
            .where("id=5")
            .generateQuery();
        assertEquals(SQL_WITH_WHERE_CLAUSE.toLowerCase(), sql.toLowerCase());
    }

    @Test
    public void shouldGenerateSqlWithWhereAndStatement() {
        String sql = queryBuilder
            .select("*")
            .from("users")
            .where(queryBuilder.and("id=5", "login='login'"))
            .generateQuery();
        assertEquals(SQL_WITH_WHERE_CLAUSE_AND.toLowerCase(), sql.toLowerCase());
    }

    @Test
    public void shouldGenerateSqlWithLeftJoin() {
        String sql = queryBuilder
            .select("*")
            .from("users")
            .leftJoin("roles r", "r.id=role_id")
            .generateQuery();
        assertEquals(SQL_WITH_LEFT_JOIN.toLowerCase(), sql.toLowerCase());
    }

    @Test
    public void shouldGenerateSqlWithGroupBy() {
        String sql = queryBuilder
            .select("*")
            .from("users")
            .groupBy("id", "login", "email")
            .generateQuery();
        assertEquals(SQL_WITH_GROUP_BY.toLowerCase(), sql.toLowerCase());
    }

    @Test
    public void shouldGenerateSqlWithOrderBy() {
        String sql = queryBuilder
            .select("*")
            .from("users")
            .orderBy("id", "login", "email")
            .generateQuery();
        assertEquals(SQL_WITH_ORDER_BY.toLowerCase(), sql.toLowerCase());
    }

    @Test
    public void shouldGenerateSqlWithMultipleTables() {
        String sql = queryBuilder
            .select("*")
            .from("users", "roles")
            .generateQuery();
        assertEquals(SQL_WITH_MULTIPLE_TABLES.toLowerCase(), sql.toLowerCase());
    }

    @Test
    public void shouldGenerateSqlWithMultipleSelect() {
        String sql = queryBuilder
            .select("users.*", "roles.*")
            .from("users", "roles")
            .generateQuery();
        assertEquals(SQL_WITH_MULTIPLE_SELECT.toLowerCase(), sql.toLowerCase());
    }
}
