package com.conferences.util.reflection.abstraction;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface IEntityParser {

    <T> T parseToEntity(Class<T> entityClass, ResultSet result, String columnPrefix) throws SQLException;

    <T> T parseToEntity(Class<T> entityClass, ResultSet result) throws SQLException;

    <T> void setValueForField(Field field, T entity, ResultSet result, String columnName);

}
