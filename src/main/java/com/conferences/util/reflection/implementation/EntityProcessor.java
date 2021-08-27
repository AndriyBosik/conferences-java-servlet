package com.conferences.util.reflection.implementation;

import com.conferences.annotation.Column;
import com.conferences.annotation.Table;
import com.conferences.model.DbTable;
import com.conferences.util.reflection.abstraction.IEntityParser;
import com.conferences.util.reflection.abstraction.IEntityProcessor;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EntityProcessor implements IEntityProcessor {

    private static Map<Class, DbTable> dbTables = new ConcurrentHashMap<>();
    private static Map<Class, List<EntityFieldData>> entityFields = new ConcurrentHashMap<>();

    private IEntityParser entityParser;

    public EntityProcessor() {
        entityParser = new EntityParser();
    }

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

        List<EntityFieldData> fields = getEntityFieldsByClass(entityClass);

        for (EntityFieldData field: fields) {
            dbTable.getFields().add(field.column.name());

            if (field.column.key()) {
                dbTable.setKey(field.column.name());
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

    @Override
    public void setEntityGeneratedFields(Object entity, ResultSet resultSet) {
        List<EntityFieldData> storedEntityFields = getEntityFieldsByClass(entity.getClass());
        for (EntityFieldData entityField: storedEntityFields) {
            if (entityField.column.key()) {
                entityParser.setValueForField(entityField.field, entity, resultSet, entityField.column.name());
            }
        }
    }

    private List<EntityFieldData> getEntityFieldsByClass(Class<?> entityClass) {
        if (entityFields.containsKey(entityClass)) {
            return entityFields.get(entityClass);
        }

        List<EntityFieldData> entityFields = new ArrayList<>();

        Field[] fields = entityClass.getDeclaredFields();
        for (Field field: fields) {
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                entityFields.add(new EntityFieldData(column, field));
            }
        }
        return entityFields;
    }

    private static class EntityFieldData {
        Column column;
        Field field;

        EntityFieldData(Column column, Field field) {
            this.column = column;
            this.field = field;
        }
    }

}
