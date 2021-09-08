package dao;

import com.conferences.dao.abstraction.*;
import com.conferences.dao.implementation.*;
import com.conferences.entity.Meeting;
import com.conferences.entity.ModeratorProposal;
import com.conferences.entity.ReportTopic;
import com.conferences.entity.User;
import custom.util.Generator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ModeratorProposalDaoTest {

    private static IModeratorProposalDao moderatorProposalDao;

    @BeforeClass
    public static void beforeTest() {
        moderatorProposalDao = new ModeratorProposalDao();

        initSpeakers();
    }

    @Test
    public void shouldDeleteModeratorProposal() {
        ModeratorProposal proposal = initDatabaseData();
        int recordsCount = moderatorProposalDao.getRecordsCount();
        moderatorProposalDao.deleteProposal(proposal);
        assertEquals(recordsCount - 1, moderatorProposalDao.getRecordsCount());
    }

    private ModeratorProposal initDatabaseData() {
        ReportTopic reportTopic = new ReportTopic();
        reportTopic.setTitle("Report Topic Title");

        IMeetingDao meetingDao = new MeetingDao();
        Meeting meeting = meetingDao.findAll().get(0);
        reportTopic.setMeetingId(meeting.getId());

        IReportTopicDao reportTopicDao = new ReportTopicDao();
        reportTopicDao.create(reportTopic);

        IUserDao userDao = new UserDao();
        User speaker = userDao.findAllByRole("speaker").get(0);

        ModeratorProposal proposal = new ModeratorProposal();
        proposal.setReportTopicId(reportTopic.getId());
        proposal.setSpeakerId(speaker.getId());

        moderatorProposalDao.create(proposal);
        return proposal;
    }

    private static void initSpeakers() {
        IRoleDao roleDao = new RoleDao();
        IUserDao userDao = new UserDao();
        int speakerRoleId = roleDao.findByTitle("speaker").getId();
        for (int i = 0; i < 10; i++) {
            User speaker = Generator.generateUser(speakerRoleId);
            userDao.create(speaker);
        }
    }
}
