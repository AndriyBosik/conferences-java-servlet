package com.conferences.util.reflection.implementation;

import com.conferences.annotation.Column;
import com.conferences.model.DbTable;
import com.conferences.util.reflection.abstraction.IEntityParser;
import com.conferences.util.reflection.abstraction.IEntityProcessor;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class EntityParser implements IEntityParser {

    private IEntityProcessor entityProcessor;

    public EntityParser() {
        entityProcessor = new EntityProcessor();
    }

    @Override
    public <T> T parseToEntity(Class<T> entityClass, ResultSet result, String columnPrefix) throws SQLException {
        try {
            DbTable dbTable = entityProcessor.getEntityFieldsList(entityClass);
            T entity = (T) Class.forName(entityClass.getName()).newInstance();

            Field[] fields = entity.getClass().getDeclaredFields();

            for (Field field: fields) {
                Column column = field.getAnnotation(Column.class);
                if (column == null) {
                    continue;
                }
                field.setAccessible(true);

                setValueForField(field, entity, result, columnPrefix + column.name());
            }

            return entity;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> T parseToEntity(Class<T> entityClass, ResultSet result) throws SQLException {
        return parseToEntity(entityClass, result, "");
    }

    private static <T> void setValueForField(Field field, T entity, ResultSet result, String columnName) throws SQLException, IllegalAccessException {
        if (field.getType().equals(Integer.class) || field.getType().equals(int.class)) {
            field.set(entity, result.getInt(columnName));
        } else if (field.getType().equals(String.class)) {
            field.set(entity, result.getString(columnName));
        } else if (field.getType().equals(Date.class)) {
            Timestamp ts = result.getTimestamp(columnName);
            Date date = new Date(ts.getTime());
            field.set(entity, date);
        }
    }

}
