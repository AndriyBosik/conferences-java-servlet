package handler;

import com.conferences.handler.abstraction.ITransactionHandler;
import com.conferences.handler.implementation.TransactionHandler;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionHandlerTest {

    private static ITransactionHandler handler;

    @BeforeClass
    public static void beforeTest() {
        handler = new TransactionHandler();
    }

    @Test(expected = Test.None.class)
    public void shouldCloseResource() throws Exception {
        AutoCloseable closeable = Mockito.mock(AutoCloseable.class);
        Mockito.doNothing().when(closeable).close();
        handler.closeResource(closeable);
    }

    @Test(expected = Test.None.class)
    public void shouldNotThrowExceptionWhenClosingResource() throws Exception {
        AutoCloseable closeable = Mockito.mock(AutoCloseable.class);
        Mockito.doThrow(new SQLException()).when(closeable).close();

        handler.closeResource(closeable);
    }

    @Test(expected = Test.None.class)
    public void shouldNotThrowExceptionForNullResource() {
        handler.closeResource(null);
    }

    @Test(expected = Test.None.class)
    public void shouldRollbackTransaction() throws Exception {
        Connection connection = Mockito.mock(Connection.class);
        Mockito.doNothing().when(connection).rollback();
        handler.rollbackTransaction(connection);
    }

    @Test(expected = Test.None.class)
    public void shouldNotThrowExceptionWhenRollingBackTransaction() throws Exception {
        Connection connection = Mockito.mock(Connection.class);
        Mockito.doThrow(new SQLException()).when(connection).rollback();

        handler.rollbackTransaction(connection);
    }

    @Test(expected = Test.None.class)
    public void shouldNotThrowExceptionForNullConnection() {
        handler.rollbackTransaction(null);
    }

    @Test(expected = Test.None.class)
    public void shouldSetAutoCommit() throws Exception {
        Connection connection = Mockito.mock(Connection.class);
        Mockito.doNothing().when(connection).setAutoCommit(true);
        handler.setAutoCommit(connection, true);
    }

    @Test(expected = Test.None.class)
    public void shouldNotThrowExceptionWhenSettingAutoCommit() throws Exception {
        Connection connection = Mockito.mock(Connection.class);
        Mockito.doThrow(new SQLException()).when(connection).setAutoCommit(true);

        handler.setAutoCommit(connection, true);
    }

    @Test(expected = Test.None.class)
    public void shouldNotThrowExceptionForSetAutoCommitForNullConnection() {
        handler.setAutoCommit(null, true);
    }
}
