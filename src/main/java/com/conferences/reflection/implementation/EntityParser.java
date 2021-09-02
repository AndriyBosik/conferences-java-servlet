package com.conferences.reflection.implementation;

import com.conferences.annotation.Column;
import com.conferences.reflection.abstraction.IEntityParser;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

public class EntityParser implements IEntityParser {

    @Override
    public <T> T parseToEntity(Class<T> entityClass, ResultSet result, String columnPrefix) throws SQLException {
        try {
            T entity = (T) Class.forName(entityClass.getName()).newInstance();

            Field[] fields = entity.getClass().getDeclaredFields();

            for (Field field: fields) {
                Column column = field.getAnnotation(Column.class);
                if (column == null) {
                    continue;
                }

                field.setAccessible(true);

                if (column.key() && result.getObject(columnPrefix + column.name()) == null) {
                    return null;
                }

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

    @Override
    public <T> void setValueForField(Field field, T entity, ResultSet result, String columnName) {
        try {
            field.setAccessible(true);
            if (field.getType().equals(Integer.class) || field.getType().equals(int.class)) {
                field.set(entity, result.getInt(columnName));
            } else if (field.getType().equals(String.class)) {
                field.set(entity, result.getString(columnName));
            } else if (field.getType().equals(LocalDateTime.class)) {
                Timestamp ts = result.getTimestamp(columnName);
                field.set(entity, ts.toLocalDateTime());
            } else if (field.getType().equals(Boolean.class) || field.getType().equals(boolean.class)) {
                field.set(entity, result.getBoolean(columnName));
            }
        } catch (SQLException | IllegalAccessException exception) {
            exception.printStackTrace();
        }
    }

}
