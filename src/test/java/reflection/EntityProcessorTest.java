package reflection;

import com.conferences.config.DbManager;
import com.conferences.entity.*;
import com.conferences.model.DbTable;
import com.conferences.reflection.abstraction.IEntityProcessor;
import com.conferences.reflection.implementation.EntityProcessor;
import custom.entity.AllAutoGeneratedFields;
import custom.entity.NoColumnAnnotation;
import custom.entity.NoTableAnnotation;
import custom.util.TimeZoneUtil;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.*;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class EntityProcessorTest {

    private static final String COLUMNS_WITH_PREFIXES = "id AS rt_id,title AS rt_title,meeting_id AS rt_meeting_id";
    private static final String UPDATE_REPORT_TOPIC_SQL = "UPDATE report_topics SET title='Report topic title',meeting_id=4 WHERE id=5";
    private static final String UPDATE_USER_MEETING_SQL = "UPDATE users_meetings SET user_id=2,meeting_id=1,present='TRUE' WHERE id=1";
    private static final String DELETE_USER_SQL = "DELETE * FROM users WHERE id=5";
    private static final String[] USERS_TABLE_COLUMNS = {
        "id", "login", "role_id", "password", "surname", "name", "email", "image_path"
    };

    private static IEntityProcessor entityProcessor;

    @BeforeClass
    public static void beforeTest() {
        entityProcessor = new EntityProcessor();
    }

    @Test
    public void shouldReturnDbDataForEntity() {
        DbTable table = entityProcessor.getEntityFieldsList(User.class);
        assertEquals("users", table.getName());
        assertEquals("id", table.getKey());
        assertEquals(USERS_TABLE_COLUMNS.length, table.getFields().size());
        for (String column: USERS_TABLE_COLUMNS) {
            assertTrue(table.getFields().contains(column.toLowerCase()));
        }
    }

    @Test
    public void shouldReturnNullForEntityWithoutTableAnnotation() {
        DbTable table = entityProcessor.getEntityFieldsList(NoTableAnnotation.class);
        assertNull(table);
    }

    @Test
    public void shouldReturnEntityFieldsListWithPrefixes() {
        String result = entityProcessor.getEntityFieldsWithPrefixes(ReportTopic.class, "", "rt_");
        assertEquals(COLUMNS_WITH_PREFIXES, result);
    }

    @Test
    public void shouldReturnEmptyStringForEntityWithoutColumns() {
        String result = entityProcessor.getEntityFieldsWithPrefixes(NoColumnAnnotation.class, "", "nca_");
        assertEquals("", result);
    }

    @Test
    public void shouldSetAutoGeneratedField() throws SQLException {
        ResultSet result = Mockito.mock(ResultSet.class);
        Mockito.when(result.getInt("id")).thenReturn(2);
        Role role = new Role();
        entityProcessor.setEntityGeneratedFields(role, result);
        assertEquals(2, role.getId());
    }

    @Test
    public void shouldPrepareInsertStatement() throws SQLException {
        Role role = new Role();
        role.setTitle("guest");
        try (Connection connection = DbManager.getInstance().getConnection();
             PreparedStatement statement = entityProcessor.prepareInsertStatement(connection, role)) {

            assertTrue(statement.toString().toLowerCase().startsWith("insert into roles(title) values ('guest')"));
        }
    }

    @Test
    public void shouldPrepareUpdateStatement() throws SQLException {
        ReportTopic reportTopic = new ReportTopic();
        reportTopic.setId(5);
        reportTopic.setTitle("Report topic title");
        reportTopic.setMeetingId(4);
        try (Connection connection = DbManager.getInstance().getConnection();
             PreparedStatement statement = entityProcessor.prepareUpdateStatement(connection, reportTopic)) {

            assertEquals(UPDATE_REPORT_TOPIC_SQL.toLowerCase(), statement.toString().toLowerCase());
        }
    }

    @Test
    public void shouldPrepareUpdateStatementForEntityWithBoolean() throws SQLException {
        UserMeeting userMeeting = new UserMeeting();
        userMeeting.setId(1);
        userMeeting.setUserId(2);
        userMeeting.setMeetingId(1);
        userMeeting.setPresent(true);
        try (Connection connection = DbManager.getInstance().getConnection();
             PreparedStatement statement = entityProcessor.prepareUpdateStatement(connection, userMeeting)) {

            assertEquals(UPDATE_USER_MEETING_SQL.toLowerCase(), statement.toString().toLowerCase());
        }
    }

    @Test
    public void shouldPrepareUpdateStatementForEntityWithDate() throws SQLException {
        Meeting meeting = new Meeting();
        meeting.setId(1);
        meeting.setTitle("Meeting title");
        meeting.setDescription("Meeting description");
        meeting.setAddress("Meeting address");
        meeting.setImagePath("meeting_image_path.png");
        LocalDateTime ldt = LocalDateTime.now();
        Timestamp ts = Timestamp.valueOf(ldt);
        meeting.setDate(ldt);
        try (Connection connection = DbManager.getInstance().getConnection();
             PreparedStatement statement = entityProcessor.prepareUpdateStatement(connection, meeting)) {

            assertEquals(("UPDATE meetings SET title='Meeting title',description='Meeting description',address='Meeting address',image_path='meeting_image_path.png',date='" + ts.toString() + TimeZoneUtil.getTimeZone() + "' WHERE id=1").toLowerCase(), statement.toString().toLowerCase());
        }
    }

    @Test
    public void shouldPrepareDeleteStatement() throws SQLException {
        User user = new User();
        user.setId(5);
        try (Connection connection = DbManager.getInstance().getConnection();
             PreparedStatement statement = entityProcessor.prepareDeleteStatement(connection, user)) {

            assertEquals(DELETE_USER_SQL.toLowerCase(), statement.toString().toLowerCase());
        }
    }

    @Test
    public void shouldReturnNullWhenPreparingUpdateStatementForEntityWithoutKeyField() throws SQLException {
        try (Connection connection = DbManager.getInstance().getConnection();
             PreparedStatement statement = entityProcessor.prepareUpdateStatement(connection, new NoColumnAnnotation())) {

            assertNull(statement);
        }
    }

    @Test
    public void shouldReturnEmptyStatementForEntityWithAllAutoGeneratedFields() throws SQLException {
        try (Connection connection = DbManager.getInstance().getConnection();
             PreparedStatement statement = entityProcessor.prepareUpdateStatement(connection, new AllAutoGeneratedFields())) {

            assertEquals("", statement.toString());
        }
    }

    @Test
    public void shouldReturnNullWhenPreparingDeleteStatementForEntityWithoutKeyColumn() throws SQLException {
        try (Connection connection = DbManager.getInstance().getConnection();
             PreparedStatement statement = entityProcessor.prepareDeleteStatement(connection, new NoColumnAnnotation())) {

            assertNull(statement);
        }
    }
}
