package com.conferences.util.reflection.implementation;

import com.conferences.annotation.Column;
import com.conferences.annotation.Key;
import com.conferences.annotation.Table;
import com.conferences.model.DbTable;
import com.conferences.util.reflection.abstraction.IEntityProcessor;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EntityProcessor implements IEntityProcessor {

    private static Map<Class, DbTable> dbTables = new ConcurrentHashMap<>();

    @Override
    public DbTable getEntityFieldsList(Class<?> entityClass) {
        if (dbTables.get(entityClass) != null) {
            return dbTables.get(entityClass);
        }

        Table table = entityClass.getAnnotation(Table.class);
        if (table == null) {
            return null;
        }

        DbTable dbTable = new DbTable();
        dbTable.setName(table.name());

        Field[] fields = entityClass.getDeclaredFields();

        for (Field field: fields) {
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                dbTable.getFields().add(column.name());
            }

            Key key = field.getAnnotation(Key.class);
            if (key != null) {
                dbTable.setKey(column.name());
            }
        }

        dbTables.put(entityClass, dbTable);

        return dbTable;
    }

    @Override
    public String getEntityFieldsWithPrefixes(Class<?> entityClass, String inPrefix, String outPrefix) {
        StringBuilder sb = new StringBuilder();

        DbTable dbTable = getEntityFieldsList(entityClass);
        for (String field: dbTable.getFields()) {
            sb.append(inPrefix).append(field).append(" AS ").append(outPrefix).append(field).append(",");
        }

        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }

}
