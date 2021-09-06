package com.conferences.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DbManager {

    private static final String DB_DRIVER = "db.driver";
    private static final String DB_CONNECTION_URL = "db.connection.url";
    private static final String DB_USERNAME = "db.username";
    private static final String DB_PASSWORD = "db.password";

    private static DbManager instance;

    private Map<String, String> dbProperties;

    private DbManager() {}

    public static DbManager getInstance() {
        if (instance == null) {
            instance = new DbManager();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        try {
            Class.forName(getPropertiesValue(DB_DRIVER));
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        return DriverManager.getConnection(getPropertiesValue(DB_CONNECTION_URL), getPropertiesValue(DB_USERNAME), getPropertiesValue(DB_PASSWORD));
    }

    private String getPropertiesValue(String key) {
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
            exception.printStackTrace();
        }
        return null;
    }

}
