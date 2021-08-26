package com.conferences.util.reflection.abstraction;

import com.conferences.model.DbTable;

public interface IEntityProcessor {

    DbTable getEntityFieldsList(Class<?> entityClass);

    String getEntityFieldsWithPrefixes(Class<?> entityClass, String inPrefix, String outPrefix);

}
