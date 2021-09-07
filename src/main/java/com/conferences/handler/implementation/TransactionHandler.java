package com.conferences.handler.implementation;

import com.conferences.handler.abstraction.ITransactionHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionHandler implements ITransactionHandler {

    private static final Logger LOGGER = LogManager.getLogger(TransactionHandler.class);

    @Override
    public void closeResource(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception exception) {
                LOGGER.error("Unable to close", exception);
            }
        }
    }

    @Override
    public void rollbackTransaction(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException exception) {
                LOGGER.error("Unable to rollback transaction", exception);
            }
        }
    }

    @Override
    public void setAutoCommit(Connection connection, boolean value) {
        if (connection != null) {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException exception) {
                LOGGER.error("Unable to set autocommit to {}", value, exception);
            }
        }
    }

}
