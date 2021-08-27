package com.conferences.util.reflection.abstraction;

import com.conferences.model.DbTable;

import java.sql.ResultSet;

public interface IEntityProcessor {

    DbTable getEntityFieldsList(Class<?> entityClass);

    String getEntityFieldsWithPrefixes(Class<?> entityClass, String inPrefix, String outPrefix);

    void setEntityGeneratedFields(Object entity, ResultSet resultSet);

}
