package com.conferences.util.reflection.implementation;

import com.conferences.annotation.Column;
import com.conferences.annotation.Table;
import com.conferences.model.DbTable;
import com.conferences.util.reflection.abstraction.IEntityParser;
import com.conferences.util.reflection.abstraction.IEntityProcessor;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EntityProcessor implements IEntityProcessor {

    private static final Map<Class<?>, DbTable> dbTables = new ConcurrentHashMap<>();
    private static final Map<Class<?>, List<EntityFieldData>> entityFieldsData = new ConcurrentHashMap<>();

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

    @Override
    public PreparedStatement prepareInsertStatement(Connection connection, Object entity) throws SQLException {
        DbTable dbTable = getEntityFieldsList(entity.getClass());
        List<EntityFieldData> entityFields = getEntityFieldsByClass(entity.getClass());

        List<String> keys = new ArrayList<>();
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        for (EntityFieldData entityField: entityFields) {
            Column column = entityField.column;
            if (column.key() && column.autoGenerated()) {
                keys.add(column.name());
            }
            if (!column.autoGenerated()) {
                columns.append(column.name()).append(",");
                values.append("?,");
            }
        }

        columns.deleteCharAt(columns.length() - 1);
        values.deleteCharAt(values.length() - 1);

        String sql = new StringBuilder("INSERT INTO ")
                .append(dbTable.getName())
                .append("(")
                .append(columns)
                .append(") VALUES (")
                .append(values)
                .append(")")
                .toString();

        PreparedStatement preparedStatement = connection.prepareStatement(sql, keys.toArray(new String[0]));

        setValuesForPreparedStatement(preparedStatement, entity, entityFields);

        return preparedStatement;
    }

    @Override
    public PreparedStatement prepareUpdateStatement(Connection connection, Object entity) throws SQLException {
        DbTable dbTable = getEntityFieldsList(entity.getClass());
        List<EntityFieldData> entityFields = getEntityFieldsByClass(entity.getClass());

        StringBuilder sqlBody = new StringBuilder();

        Field keyField = null;
        int toModify = 0;
        for (EntityFieldData entityField: entityFields) {
            if (entityField.column.key()) {
                entityField.field.setAccessible(true);
                keyField = entityField.field;
            }
            if (!entityField.column.autoGenerated()) {
                toModify++;
                sqlBody.append(entityField.column.name()).append("=?,");
            }
        }

        if (keyField == null) {
            return null;
        }

        if (sqlBody.length() == 0) {
            return connection.prepareStatement("");
        }

        sqlBody.deleteCharAt(sqlBody.length() - 1);

        String sql = new StringBuilder("UPDATE ")
                .append(dbTable.getName())
                .append(" SET ")
                .append(sqlBody)
                .append(" WHERE ")
                .append(dbTable.getKey())
                .append("=?")
                .toString();

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        setValuesForPreparedStatement(preparedStatement, entity, entityFields);
        setPreparedStatementValue(preparedStatement, toModify + 1, entity, keyField);

        return preparedStatement;
    }

    @Override
    public PreparedStatement prepareDeleteStatement(Connection connection, Object entity) throws SQLException {
        DbTable dbTable = getEntityFieldsList(entity.getClass());
        List<EntityFieldData> entityFields = getEntityFieldsByClass(entity.getClass());
        String sql = "DELETE * FROM " + dbTable.getName() + " WHERE " + dbTable.getKey() + "=?";
        for (EntityFieldData entityField: entityFields) {
            if (entityField.column.key()) {
                PreparedStatement statement = connection.prepareStatement(sql);
                setPreparedStatementValue(statement, 1, entity, entityField.field);
                return statement;
            }
        }
        return null;
    }

    private <T> void setValuesForPreparedStatement(PreparedStatement preparedStatement, T entity, List<EntityFieldData> entityFields) throws SQLException {
        int number = 1;
        for (EntityFieldData entityField: entityFields) {
            if (entityField.column.autoGenerated()) {
                continue;
            }
            setPreparedStatementValue(preparedStatement, number, entity, entityField.field);
            number++;
        }
    }

    private void setPreparedStatementValue(PreparedStatement preparedStatement, int number, Object entity, Field field) throws SQLException {
        try {
            field.setAccessible(true);
            if (field.getType().equals(Integer.class)) {
                preparedStatement.setObject(number, field.get(entity));
            } else if (field.getType().equals(int.class)) {
                preparedStatement.setInt(number, field.getInt(entity));
            } else if (field.getType().equals(String.class)) {
                preparedStatement.setString(number, (String) field.get(entity));
            } else if (field.getType().equals(Date.class)) {
                Date date = (Date) field.get(entity);
                preparedStatement.setTimestamp(number, new Timestamp(date.getTime()));
            }
        } catch (IllegalAccessException exception) {
            exception.printStackTrace();
        }
    }

    private List<EntityFieldData> getEntityFieldsByClass(Class<?> entityClass) {
        if (entityFieldsData.containsKey(entityClass)) {
            return entityFieldsData.get(entityClass);
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
