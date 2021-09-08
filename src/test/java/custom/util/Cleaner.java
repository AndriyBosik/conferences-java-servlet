package custom.util;

import com.conferences.dao.abstraction.*;
import com.conferences.dao.implementation.*;
import com.conferences.entity.*;

import java.util.List;

public class Cleaner {

    private static final IReportTopicSpeakerDao reportTopicSpeakerDao;
    private static final ISpeakerProposalDao speakerProposalDao;
    private static final ITopicProposalDao topicProposalDao;
    private static final IUserMeetingDao userMeetingDao;
    private static final IReportTopicDao reportTopicDao;
    private static final IMeetingDao meetingDao;
    private static final IUserDao userDao;

    static {
        reportTopicSpeakerDao = new ReportTopicSpeakerDao();
        speakerProposalDao = new SpeakerProposalDao();
        topicProposalDao = new TopicProposalDao();
        userMeetingDao = new UserMeetingDao();
        reportTopicDao = new ReportTopicDao();
        meetingDao = new MeetingDao();
        userDao = new UserDao();
    }

    public static void clearMeetingsTable() {
        List<Meeting> meetings = meetingDao.findAll();
        for (Meeting meeting: meetings) {
            meetingDao.delete(meeting.getId());
        }
    }

    public static void clearUsersTable() {
        List<User> users = userDao.findAll();
        for (User user: users) {
            userDao.delete(user.getId());
        }
    }

    public static void clearTopicProposalsTable() {
        List<TopicProposal> topicProposals = topicProposalDao.findAll();
        for (TopicProposal topicProposal: topicProposals) {
            topicProposalDao.delete(topicProposal.getId());
        }
    }

    public static void clearReportTopicsTable() {
        List<ReportTopic> reportTopics = reportTopicDao.findAll();
        for (ReportTopic reportTopic: reportTopics) {
            reportTopicDao.delete(reportTopic.getId());
        }
    }

    public static void clearSpeakerProposalsTable() {
        List<SpeakerProposal> speakerProposals = speakerProposalDao.findAll();
        for (SpeakerProposal speakerProposal: speakerProposals) {
            speakerProposalDao.delete(speakerProposal.getId());
        }
    }

    public static void clearReportTopicsSpeakersTable() {
        List<ReportTopicSpeaker> reportTopicsSpeakers = reportTopicSpeakerDao.findAll();
        for (ReportTopicSpeaker reportTopicSpeaker: reportTopicsSpeakers) {
            reportTopicSpeakerDao.delete(reportTopicSpeaker.getId());
        }
    }

    public static void clearUsersMeetingsTable() {
        List<UserMeeting> usersMeetings = userMeetingDao.findAll();
        for (UserMeeting userMeeting: usersMeetings) {
            userMeetingDao.delete(userMeeting.getId());
        }
    }
}
