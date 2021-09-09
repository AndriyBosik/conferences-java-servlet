package com.conferences.reflection.abstraction;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>
 *     Defines methods to parse {@link ResultSet} to entity
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public interface IEntityParser {

    /**
     * <p>
     *     Parses {@link ResultSet} to object of {@code entityClass} class
     * </p>
     * @param entityClass class of object to parse to
     * @param result {@link ResultSet} containing database data
     * @param columnPrefix prefix which all columns have
     * @param <T> type of returned object
     * @return entity of T type
     * @throws SQLException exception which may occur during parsing
     */
    <T> T parseToEntity(Class<T> entityClass, ResultSet result, String columnPrefix) throws SQLException;

    /**
     * <p>
     *     Parses {@link ResultSet} to object of {@code entityClass} class
     * </p>
     * @param entityClass class of object to parse to
     * @param result {@link ResultSet} containing database data
     * @param <T> type of returned object
     * @return entity of T type
     * @throws SQLException exception which may occur during parsing
     */
    <T> T parseToEntity(Class<T> entityClass, ResultSet result) throws SQLException;

    /**
     * <p>
     *     Sets value for entity's field
     * </p>
     * @param field field to assign value for
     * @param entity entity to assign value for field for
     * @param result {@link ResultSet} containing field data
     * @param columnName name of column to get data from
     */
    void setValueForField(Field field, Object entity, ResultSet result, String columnName);

}
