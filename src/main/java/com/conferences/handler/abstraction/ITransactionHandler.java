package com.conferences.handler.abstraction;

import java.sql.Connection;

/**
 * <p>
 *     Defines methods to handle with transaction
 * </p>
 */
public interface ITransactionHandler {

    /**
     * <p>
     *     Closes resource
     * </p>
     * @param closeable resource which needs to be closed
     */
    void closeResource(AutoCloseable closeable);

    /**
     * <p>
     *     Rollbacks transaction
     * </p>
     * @param connection SQL connection on which transaction should be rolled back
     */
    void rollbackTransaction(Connection connection);

    /**
     * <p>
     *     Sets auto commit for connection
     * </p>
     * @param connection SQL connection to set AutoCommit on
     * @param value AutoCommit value
     */
    void setAutoCommit(Connection connection, boolean value);
}
