package dao;

import com.conferences.dao.abstraction.*;
import com.conferences.dao.implementation.*;
import com.conferences.entity.Meeting;
import com.conferences.entity.TopicProposal;
import com.conferences.entity.User;
import com.conferences.entity.custom.ProposedTopicData;
import custom.util.Cleaner;
import custom.util.Generator;
import custom.util.RandomUtil;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ProposedTopicDataDaoTest {

    private static IProposedTopicDataDao proposedTopicDataDao;
    private static Map<Integer, TopicProposal> topicProposals;
    private static List<Meeting> meetings;
    private static List<User> users;
    private static SecureRandom random;
    private static int speakerRoleId;
    private static int topicProposalsCount;
    private static int meetingsCount;
    private static int usersCount;

    @BeforeClass
    public static void beforeTest() {
        IRoleDao roleDao = new RoleDao();
        speakerRoleId = roleDao.findByTitle("speaker").getId();

        proposedTopicDataDao = new ProposedTopicDataDao();
        topicProposals = new HashMap<>();
        meetings = new ArrayList<>();
        users = new ArrayList<>();
        random = new SecureRandom();

        meetingsCount = 10;
        usersCount = 8;
        topicProposalsCount = 6;

        clearDatabase();
        initMeetings();
        initUsers();
        initProposedTopicData();
    }

    @AfterClass
    public static void afterTest() {
        clearDatabase();
    }

    @Test
    public void shouldFindAllProposedTopicsOrderByMeetingId() {
        List<ProposedTopicData> proposedTopicsData = proposedTopicDataDao.findAllProposedTopicsOrderByMeetingId();
        assertEquals(proposedTopicsData.size(), topicProposalsCount);
        for (ProposedTopicData proposedTopic: proposedTopicsData) {
            TopicProposal topicProposal = topicProposals.get(proposedTopic.getId());
            assertEquals(topicProposal.getId(), proposedTopic.getId());
            assertEquals(topicProposal.getTopicTitle(), proposedTopic.getProposedTopicTitle());
            assertEquals(topicProposal.getMeetingId(), proposedTopic.getMeetingId());
        }
    }

    private static void clearDatabase() {
        Cleaner.clearTopicProposalsTable();
        Cleaner.clearMeetingsTable();
        Cleaner.clearUsersTable();
    }

    private static void initMeetings() {
        IMeetingDao meetingDao = new MeetingDao();
        for (int i = 0; i < meetingsCount; i++) {
            Meeting meeting = Generator.generateMeeting();
            meetingDao.create(meeting);
            meetings.add(meeting);
        }
    }

    private static void initUsers() {
        IUserDao userDao = new UserDao();
        for (int i = 0; i < usersCount; i++) {
            User user = Generator.generateUser(speakerRoleId);
            userDao.create(user);
            users.add(user);
        }
    }

    private static void initProposedTopicData() {
        User speaker = getRandomSpeaker();
        Meeting meeting = getRandomMeeting();

        ITopicProposalDao topicProposalDao = new TopicProposalDao();
        for (int i = 0; i < topicProposalsCount; i++) {
            TopicProposal topicProposal = new TopicProposal();
            topicProposal.setTopicTitle(RandomUtil.generateRandomString(25));
            topicProposal.setSpeakerId(speaker.getId());
            topicProposal.setMeetingId(meeting.getId());

            topicProposalDao.create(topicProposal);
            topicProposals.put(topicProposal.getId(), topicProposal);
        }
    }

    private static User getRandomSpeaker() {
        int index = random.nextInt(usersCount);
        return users.get(index);
    }

    private static Meeting getRandomMeeting() {
        int index = random.nextInt(meetingsCount);
        return meetings.get(index);
    }
}
