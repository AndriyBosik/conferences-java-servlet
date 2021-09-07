package config;

import com.conferences.config.DbManager;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class DbManagerTest {

    @Test
    public void shouldReturnConnection() throws SQLException {
        Connection connection = DbManager.getInstance().getConnection();
        assertNotNull(connection);
    }

}
