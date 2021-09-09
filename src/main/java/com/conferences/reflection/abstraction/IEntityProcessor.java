package com.conferences.reflection.abstraction;

import com.conferences.model.DbTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>
 *     Defines methods to process entity objects
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public interface IEntityProcessor {

    /**
     * <p>
     *     Extracts {@link DbTable} from {@code entityClass}
     * </p>
     * @param entityClass class to extract {@link DbTable} data from
     * @return {@link DbTable} data from {@code entityClass}
     */
    DbTable getDbTableData(Class<?> entityClass);

    /**
     * <p>
     *      Returns part of SQL string like "{inPrefix}columnName1 AS {outPrefix}columnName1,{inPrefix}columnName2 AS {outPrefix}columnName2..."
     * </p>
     * @param entityClass class of entity to extract column names from
     * @param inPrefix column prefix to get full column name
     * @param outPrefix column prefix to assign column alias
     * @return part of SQL string
     */
    String getEntityFieldsWithPrefixes(Class<?> entityClass, String inPrefix, String outPrefix);

    /**
     * <p>
     *     Sets generated fields for entity
     * </p>
     * @param entity entity to set generated fields to
     * @param resultSet {@link ResultSet} to extract generated fields from
     */
    void setEntityGeneratedFields(Object entity, ResultSet resultSet);

    /**
     * <p>
     *     Creates {@link PreparedStatement} for inserting entity to database
     * </p>
     * @param connection connection to get prepared statement from
     * @param entity entity to get data from
     * @return {@link PreparedStatement} to execute insert to database
     * @throws SQLException may occur during statement preparing
     */
    PreparedStatement prepareInsertStatement(Connection connection, Object entity) throws SQLException;

    /**
     * <p>
     *     Creates {@link PreparedStatement} for updating entity in database
     * </p>
     * @param connection connection to get prepared statement from
     * @param entity entity to get data from
     * @return {@link PreparedStatement} to execute update to database
     * @throws SQLException may occur during statement preparing
     */
    PreparedStatement prepareUpdateStatement(Connection connection, Object entity) throws SQLException;

    /**
     * <p>
     *     Creates {@link PreparedStatement} for deletion entity to database
     * </p>
     * @param connection connection to get prepared statement from
     * @param entity entity to get data from
     * @return {@link PreparedStatement} to execute delete from database
     * @throws SQLException may occur during statement preparing
     */
    PreparedStatement prepareDeleteStatement(Connection connection, Object entity) throws SQLException;
}
