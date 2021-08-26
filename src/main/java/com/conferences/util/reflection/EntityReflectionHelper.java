package com.conferences.util.reflection;

import com.conferences.annotation.Column;
import com.conferences.annotation.Key;
import com.conferences.annotation.Table;
import com.conferences.model.DbTable;

import java.lang.reflect.Field;

public class EntityReflectionHelper {

    public static DbTable getEntityFieldsList(Class<?> entityClass) {
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
        return dbTable;
    }

}
