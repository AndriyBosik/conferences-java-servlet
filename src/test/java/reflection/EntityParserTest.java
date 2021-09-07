package reflection;

import com.conferences.entity.Meeting;
import com.conferences.reflection.abstraction.IEntityParser;
import com.conferences.reflection.implementation.EntityParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class EntityParserTest {

    private static final int ID = 1;
    private static final String TITLE = "Spring Core";
    private static final String DESCRIPTION = "Spring Core description";
    private static final String ADDRESS = "Spring Core address";
    private static final String IMAGE_PATH = "meeting_070920211432.png";
    private static final Timestamp DATE = Timestamp.valueOf(LocalDateTime.now());

    private static IEntityParser entityParser;

    private ResultSet result;

    @BeforeClass
    public static void beforeTest() {
        entityParser = new EntityParser();
    }

    @Test
    public void shouldParseMeetingEntityWithoutColumnPrefix() throws SQLException {
        configureResultSetMock("");
        Meeting meeting = entityParser.parseToEntity(Meeting.class, result);

        assertEquals(1, meeting.getId());
        assertEquals("Spring Core", meeting.getTitle());
        assertEquals("Spring Core description", meeting.getDescription());
        assertEquals("Spring Core address", meeting.getAddress());
        assertEquals("meeting_070920211432.png", meeting.getImagePath());
    }

    @Test
    public void shouldParseMeetingEntityWithColumnPrefix() throws SQLException {
        configureResultSetMock("meeting_");
        Meeting meeting = entityParser.parseToEntity(Meeting.class, result, "meeting_");

        assertEquals(1, meeting.getId());
        assertEquals("Spring Core", meeting.getTitle());
        assertEquals("Spring Core description", meeting.getDescription());
        assertEquals("Spring Core address", meeting.getAddress());
        assertEquals("meeting_070920211432.png", meeting.getImagePath());
    }

    private void configureResultSetMock(String columnPrefix) throws SQLException {
        result = Mockito.mock(ResultSet.class);
        Mockito.when(result.findColumn(columnPrefix + "id")).thenReturn(1);
        Mockito.when(result.findColumn(columnPrefix + "title")).thenReturn(2);
        Mockito.when(result.findColumn(columnPrefix + "description")).thenReturn(3);
        Mockito.when(result.findColumn(columnPrefix + "address")).thenReturn(4);
        Mockito.when(result.findColumn(columnPrefix + "image_path")).thenReturn(5);
        Mockito.when(result.findColumn(columnPrefix + "date")).thenReturn(6);

        Mockito.when(result.getObject(columnPrefix + "id")).thenReturn(ID);
        Mockito.when(result.getObject(columnPrefix + "title")).thenReturn(TITLE);
        Mockito.when(result.getObject(columnPrefix + "description")).thenReturn(DESCRIPTION);
        Mockito.when(result.getObject(columnPrefix + "address")).thenReturn(ADDRESS);
        Mockito.when(result.getObject(columnPrefix + "image_path")).thenReturn(IMAGE_PATH);
        Mockito.when(result.getObject(columnPrefix + "date")).thenReturn(DATE);

        Mockito.when(result.getInt(columnPrefix + "id")).thenReturn(ID);
        Mockito.when(result.getString(columnPrefix + "title")).thenReturn(TITLE);
        Mockito.when(result.getString(columnPrefix + "description")).thenReturn(DESCRIPTION);
        Mockito.when(result.getString(columnPrefix + "address")).thenReturn(ADDRESS);
        Mockito.when(result.getString(columnPrefix + "image_path")).thenReturn(IMAGE_PATH);
        Mockito.when(result.getTimestamp(columnPrefix + "date")).thenReturn(DATE);
    }

}
