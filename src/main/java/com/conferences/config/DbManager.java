package com.conferences.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbManager {

    private static DbManager instance;

    private DbManager() {

    }

    public static DbManager getInstance() {
        if (instance == null) {
            instance = new DbManager();
        }
        return instance;
    }

    public static Connection getConnection() {
        try {
            if (!org.postgresql.Driver.isRegistered()) {
                org.postgresql.Driver.register();
            }
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/conferences", "postgres", "postgres");
        } catch (Exception exception) {
            return null;
        }
    }

}
