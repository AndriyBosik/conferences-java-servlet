package dao;

import com.conferences.dao.abstraction.IReportTopicSpeakerDao;
import com.conferences.dao.implementation.ReportTopicSpeakerDao;
import com.conferences.handler.abstraction.ITransactionHandler;
import com.conferences.handler.implementation.TransactionHandler;
import org.junit.BeforeClass;
import org.mockito.Mockito;

import java.lang.reflect.Field;

public class ReportTopicSpeakerDaoTest {

    private static IReportTopicSpeakerDao reportTopicSpeakerDao;

    @BeforeClass
    public static void beforeTest() throws NoSuchFieldException {
        reportTopicSpeakerDao = new ReportTopicSpeakerDao();
        Field transactionHandlerField = ReportTopicSpeakerDao.class.getField("transactionHandler");
        ITransactionHandler handler = getTransactionHandlerMock();
    }

    private static ITransactionHandler getTransactionHandlerMock() {
        ITransactionHandler handler = Mockito.mock(ITransactionHandler.class);
//        Mockito.when(handler.rollbackTransaction()).
        return handler;
    }
}
