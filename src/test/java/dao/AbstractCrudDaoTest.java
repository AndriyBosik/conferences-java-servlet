package dao;

import com.conferences.dao.abstraction.AbstractCrudDao;
import com.conferences.entity.Meeting;
import custom.util.Cleaner;
import custom.util.Generator;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AbstractCrudDaoTest {

    private static AbstractCrudDao<Integer, Meeting> meetingCrudDao;

    @BeforeClass
    public static void beforeTest() {
        meetingCrudDao = new AbstractCrudDao<Integer, Meeting>() {};

        addMettings();
    }

    @Test
    public void shouldCreateMeeting() {
        Meeting meeting = Generator.generateMeeting();
        meeting.setId(-1);

        meetingCrudDao.create(meeting);
        assertNotEquals(-1, meeting.getId());
    }

    @Test
    public void shouldReadAllMeetings() {
        Cleaner.clearMeetingsTable();
        Meeting generatedMeeting;
        List<Integer> ids = new ArrayList<>();
        generatedMeeting = Generator.generateMeeting();
        meetingCrudDao.create(generatedMeeting);
        ids.add(generatedMeeting.getId());
        generatedMeeting = Generator.generateMeeting();
        meetingCrudDao.create(generatedMeeting);
        ids.add(generatedMeeting.getId());
        generatedMeeting = Generator.generateMeeting();
        meetingCrudDao.create(generatedMeeting);
        ids.add(generatedMeeting.getId());
        List<Meeting> meetings = meetingCrudDao.findAll();
        for (int i = 0; i < meetings.size(); i++) {
            assertEquals((long)ids.get(i), meetings.get(i).getId());
        }
    }

    @Test
    public void shouldUpdateMeeting() {
        List<Meeting> meetings = meetingCrudDao.findAll();
        if (meetings.isEmpty()) {
            fail();
        }
        String newTitle = meetings.get(0).getTitle() + " new";
        meetings.get(0).setTitle(newTitle);
        meetingCrudDao.update(meetings.get(0));
        Meeting meeting = meetingCrudDao.find(meetings.get(0).getId());
        assertEquals(newTitle, meeting.getTitle());
    }

    @Test
    public void shouldDeleteMeeting() {
        List<Meeting> meetings = meetingCrudDao.findAll();
        if (meetings.isEmpty()) {
            fail();
        }
        int meetingsCount = meetings.size();
        meetingCrudDao.delete(meetings.get(0).getId());
        int newMeetingsCount = meetingCrudDao.findAll().size();
        assertEquals(meetingsCount - 1, newMeetingsCount);
    }

    @Test
    public void shouldReturnNullWhenRecordNotFound() {
        Meeting meeting = meetingCrudDao.find(-1);
        assertNull(meeting);
    }

    @Test
    public void shouldReturnCorrectRecordsCount() {
        Cleaner.clearMeetingsTable();
        int recordsCount = 10;
        for (int i = 0; i < recordsCount; i++) {
            Meeting meeting = Generator.generateMeeting();
            meetingCrudDao.create(meeting);
        }
        assertEquals(recordsCount, meetingCrudDao.getRecordsCount());
    }

    private static void addMettings() {
        for (int i = 0; i < 5; i++) {
            meetingCrudDao.create(Generator.generateMeeting());
        }
    }
}
