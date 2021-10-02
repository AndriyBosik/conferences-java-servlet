package com.conferences.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DbManager {

    private static final Logger LOGGER = LogManager.getLogger(DbManager.class);

    private static DbManager instance;

    private final DataSource dataSource;

    private Map<String, String> dbProperties;

    private DbManager() {
        try {
            InitialContext context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:comp/env/jdbc/conferences");
        } catch (Exception exception) {
            LOGGER.error("Unable to register database driver", exception);
            throw new IllegalStateException("Cannot init DbManager", exception);
        }
    }

    public static DbManager getInstance() {
        if (instance == null) {
            instance = new DbManager();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    private String getPropertiesValue(String key) {
        LOGGER.info("Getting {} property value", key);
        if (dbProperties != null) {
            return dbProperties.get(key);
        }
        try (InputStream input = DbManager.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            dbProperties = new HashMap<>();
            for (Map.Entry<Object, Object> entry: properties.entrySet()) {
                dbProperties.put((String) entry.getKey(), (String) entry.getValue());
            }
            return dbProperties.get(key);
        } catch (IOException exception) {
            LOGGER.error("Unable to get property value", exception);
        }
        return null;
    }

}
