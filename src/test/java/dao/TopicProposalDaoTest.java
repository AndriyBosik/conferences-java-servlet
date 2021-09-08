package dao;

import com.conferences.dao.abstraction.*;
import com.conferences.dao.implementation.*;
import com.conferences.entity.Meeting;
import com.conferences.entity.TopicProposal;
import com.conferences.entity.User;
import com.conferences.handler.abstraction.ITransactionHandler;
import custom.util.Cleaner;
import custom.util.Generator;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Arrays;

import static org.junit.Assert.*;

public class TopicProposalDaoTest {

    private static int speakerRoleId;
    private static int topicProposalId;
    private static ITopicProposalDao topicProposalDao;
    private static IReportTopicDao reportTopicDao;
    private static IMeetingDao meetingDao;
    private static IUserDao userDao;

    @BeforeClass
    public static void beforeTest() {
        IRoleDao roleDao = new RoleDao();
        speakerRoleId = roleDao.findByTitle("speaker").getId();

        Cleaner.clearUsersTable();
        Cleaner.clearMeetingsTable();
        Cleaner.clearReportTopicsTable();
        Cleaner.clearTopicProposalsTable();

        topicProposalDao = new TopicProposalDao();
        reportTopicDao = new ReportTopicDao();
        meetingDao = new MeetingDao();
        userDao = new UserDao();

        topicProposalId = initData();
    }

    @Test
    public void shouldRollbackTransaction() throws IllegalAccessException {
        TopicProposalDao dao = new TopicProposalDao();
        Field transactionHandlerField = Arrays.stream(TopicProposalDao.class.getSuperclass().getSuperclass().getDeclaredFields())
            .filter(field -> field.getName().equals("transactionHandler"))
            .findFirst()
            .orElse(null);
        transactionHandlerField.setAccessible(true);
        ITransactionHandler handlerMock = getTransactionHandlerMock();
        transactionHandlerField.set(dao, handlerMock);

        dao.createReportTopicWithProposalDeletion(1);

        Mockito.verify(handlerMock, Mockito.times(1)).rollbackTransaction(Matchers.any(Connection.class));
    }

    @Test
    public void shouldCreateReportTopicWithProposalDeletion() {
        topicProposalDao.createReportTopicWithProposalDeletion(topicProposalId);

        assertEquals(0, topicProposalDao.findAll().size());
        assertEquals(1, reportTopicDao.findAll().size());
    }

    private ITransactionHandler getTransactionHandlerMock() {
        ITransactionHandler handler = Mockito.mock(ITransactionHandler.class);
        Mockito.doNothing().when(handler).setAutoCommit(Matchers.any(Connection.class), Matchers.anyBoolean());
        Mockito.doNothing().when(handler).rollbackTransaction(Matchers.any(Connection.class));
        return handler;
    }

    private static int initData() {
        User user = Generator.generateUser(speakerRoleId);
        userDao.create(user);
        Meeting meeting = Generator.generateMeeting();
        meetingDao.create(meeting);
        TopicProposal topicProposal = Generator.generateTopicProposal(user.getId(), meeting.getId());

        topicProposalDao.create(topicProposal);
        return topicProposal.getId();
    }
}
