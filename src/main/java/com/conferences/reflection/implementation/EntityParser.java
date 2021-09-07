package com.conferences.reflection.implementation;

import com.conferences.annotation.Column;
import com.conferences.reflection.abstraction.IEntityParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class EntityParser implements IEntityParser {

    private static final Logger LOGGER = LogManager.getLogger(EntityParser.class);

    @Override
    public <T> T parseToEntity(Class<T> entityClass, ResultSet result, String columnPrefix) throws SQLException {
        try {
            T entity = (T) Class.forName(entityClass.getName()).newInstance();

            Field[] fields = entity.getClass().getDeclaredFields();

            for (Field field: fields) {
                Column column = field.getAnnotation(Column.class);
                field.setAccessible(true);
                if (column == null || !columnExists(result, columnPrefix + column.name())) {
                    continue;
                }

                if (column.key() && result.getObject(columnPrefix + column.name()) == null) {
                    return null;
                }

                setValueForField(field, entity, result, columnPrefix + column.name());
            }

            return entity;
        } catch (Exception exception) {
            LOGGER.error("Unable to parse entity", exception);
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
                System.out.println(ts);
                field.set(entity, ts.toLocalDateTime());
            } else if (field.getType().equals(Boolean.class) || field.getType().equals(boolean.class)) {
                field.set(entity, result.getBoolean(columnName));
            }
        } catch (SQLException | IllegalAccessException exception) {
            LOGGER.error("Unable to set value for '{}' field", field.getName(), exception);
        }
    }

    private boolean columnExists(ResultSet result, String columnName) {
        try {
            result.findColumn(columnName);
            return true;
        } catch (SQLException exception) {
            LOGGER.error("Column {} not found", columnName, exception);
        }
        return false;
    }

}
