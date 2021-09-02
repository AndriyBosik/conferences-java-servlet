package com.conferences.handler.implementation;

import com.conferences.handler.abstraction.ITransactionHandler;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionHandler implements ITransactionHandler {

    @Override
    public void closeResource(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void rollbackTransaction(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void setAutoCommit(Connection connection, boolean value) {
        if (connection != null) {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

}
