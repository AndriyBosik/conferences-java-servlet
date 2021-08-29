package com.conferences.util.reflection.abstraction;

import com.conferences.model.DbTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface IEntityProcessor {

    DbTable getEntityFieldsList(Class<?> entityClass);

    String getEntityFieldsWithPrefixes(Class<?> entityClass, String inPrefix, String outPrefix);

    void setEntityGeneratedFields(Object entity, ResultSet resultSet);

    PreparedStatement prepareInsertStatement(Connection connection, Object entity) throws SQLException;

    PreparedStatement prepareUpdateStatement(Connection connection, Object entity) throws SQLException;

    PreparedStatement prepareDeleteStatement(Connection connection, Object entity) throws SQLException;

}
