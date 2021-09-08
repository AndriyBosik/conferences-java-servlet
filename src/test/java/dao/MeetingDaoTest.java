package dao;

import com.conferences.dao.abstraction.IMeetingDao;
import com.conferences.dao.implementation.MeetingDao;
import com.conferences.entity.Meeting;
import com.conferences.model.MeetingSorter;
import com.conferences.model.Page;
import com.conferences.model.PageResponse;
import custom.initializer.MeetingInitializer;
import custom.util.RandomUtil;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class MeetingDaoTest {

    private static IMeetingDao meetingDao;
    private static MeetingInitializer initializer;
    private static int meetingsSize;

    @BeforeClass
    public static void beforeTest() {
        meetingDao = new MeetingDao();
        initializer = new MeetingInitializer();
        initializer.init();
        meetingsSize = initializer.getMeetings().size();
    }

    @Test
    public void shouldUpdateMeetingEditableData() {
        Meeting meeting = initializer.getMeetings().get(RandomUtil.generateInt(meetingsSize));
        meeting.setAddress("!!!Address with punctuation marks!!!");
        LocalDateTime ldt = LocalDateTime.now().plusDays(2);
        meeting.setDate(ldt);
        meetingDao.updateMeetingEditableData(meeting);
        Meeting updatedMeeting = meetingDao.find(meeting.getId());
        assertEquals(meeting.getAddress(), updatedMeeting.getAddress());
        assertEquals(meeting.getDate(), updatedMeeting.getDate());
    }

    @Test
    public void shouldFindAllDataByMeetingId() {
        int index = RandomUtil.generateInt(meetingsSize);
        int meetingId = initializer.getMeetings().get(index).getId();
        Meeting meeting = initializer.findByKeyWithReportTopicsAndSpeakersAndUsersCount(meetingId);
        Meeting databaseMeeting = meetingDao.findByKeyWithReportTopicsAndSpeakersAndUsersCount(meetingId);

        assertEquals(meeting.getUsersCount(), databaseMeeting.getUsersCount());
        assertEquals(meeting.getReportTopicsCount(), databaseMeeting.getReportTopicsCount());
        assertEquals(meeting.getId(), databaseMeeting.getId());
        for (int i = 0; i < meeting.getReportTopics().size(); i++) {
            assertEquals(
                meeting.getReportTopics().get(i).getId(),
                databaseMeeting.getReportTopics().get(i).getId()
            );
            assertEquals(
                meeting.getReportTopics().get(i).getTitle(),
                databaseMeeting.getReportTopics().get(i).getTitle()
            );
            assertEquals(
                meeting.getReportTopics().get(i).getReportTopicSpeaker().getId(),
                databaseMeeting.getReportTopics().get(i).getReportTopicSpeaker().getId()
            );
            assertEquals(
                meeting.getReportTopics().get(i).getReportTopicSpeaker().getSpeaker().getId(),
                databaseMeeting.getReportTopics().get(i).getReportTopicSpeaker().getSpeaker().getId()
            );
        }
    }

    @Test
    public void shouldFindAllPageBySorterWithUsersCountAndTopicsCount() {
        Page page = new Page(2, 2);
        MeetingSorter sorter = new MeetingSorter();
        PageResponse<Meeting> meetings = initializer.findAllPageBySorterWithUsersCountAndTopicsCount(page, sorter);
        PageResponse<Meeting> databaseMeetings = meetingDao.findAllPageBySorterWithUsersCountAndTopicsCount(page, sorter);
        assertEquals(meetings.getPagesCount(), databaseMeetings.getPagesCount());
        for (int i = 0; i < meetings.getItems().size(); i++) {
            assertEquals(
                meetings.getItems().get(i).getId(),
                databaseMeetings.getItems().get(i).getId());
            assertEquals(
                    meetings.getItems().get(i).getReportTopicsCount(),
                    databaseMeetings.getItems().get(i).getReportTopicsCount());
            assertEquals(
                    meetings.getItems().get(i).getUsersCount(),
                    databaseMeetings.getItems().get(i).getUsersCount());
            assertEquals(
                    meetings.getItems().get(i).getTitle(),
                    databaseMeetings.getItems().get(i).getTitle());
        }
    }

    @Test
    public void shouldFindAllSpeakerMeetingsPageBySorterWithUsersCountAndTopicsCount() {
        int speakersSize = initializer.getSpeakers().size();
        int speakerId = initializer.getSpeakers().get(RandomUtil.generateInt(speakersSize)).getId();
        Page page = new Page(2, 2);
        MeetingSorter sorter = new MeetingSorter();
        PageResponse<Meeting> meetings = initializer.findAllSpeakerMeetingsPageBySorterWithUsersCountAndTopicsCount(page, sorter, speakerId);
        PageResponse<Meeting> databaseMeetings = meetingDao.findAllSpeakerMeetingsPageBySorterWithUsersCountAndTopicsCount(page, sorter, speakerId);
        assertEquals(meetings.getPagesCount(), databaseMeetings.getPagesCount());
        for (int i = 0; i < meetings.getItems().size(); i++) {
            assertEquals(
                    meetings.getItems().get(i).getId(),
                    databaseMeetings.getItems().get(i).getId());
            assertEquals(
                    meetings.getItems().get(i).getReportTopicsCount(),
                    databaseMeetings.getItems().get(i).getReportTopicsCount());
            assertEquals(
                    meetings.getItems().get(i).getUsersCount(),
                    databaseMeetings.getItems().get(i).getUsersCount());
            assertEquals(
                    meetings.getItems().get(i).getTitle(),
                    databaseMeetings.getItems().get(i).getTitle());
        }
    }
}
