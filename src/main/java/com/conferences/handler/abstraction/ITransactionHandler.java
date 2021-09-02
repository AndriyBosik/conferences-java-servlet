package com.conferences.handler.abstraction;

import java.sql.Connection;

public interface ITransactionHandler {

    void closeResource(AutoCloseable closeable);

    void rollbackTransaction(Connection connection);

    void setAutoCommit(Connection connection, boolean value);

}
