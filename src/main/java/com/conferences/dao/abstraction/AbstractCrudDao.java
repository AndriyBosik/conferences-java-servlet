package com.conferences.dao.abstraction;

import com.conferences.config.DbManager;
import com.conferences.model.DbTable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCrudDao<K, T> extends AbstractDao<K, T> implements ICrudDao<K, T> {

    private static final Logger LOGGER = LogManager.getLogger(AbstractCrudDao.class);

    private final Class<T> entityClass;

    protected final DbTable dbTable;

    protected AbstractCrudDao() {
        entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];

        dbTable = entityProcessor.getEntityFieldsList(entityClass);
    }

    @Override
    public boolean create(T model) {
        int numberOfInsertedRows = 0;
        LOGGER.info("Creating an entity of {} class", model.getClass());
        try (Connection connection = DbManager.getInstance().getConnection();
             PreparedStatement statement = entityProcessor.prepareInsertStatement(connection, model)) {

            numberOfInsertedRows = statement.executeUpdate();

            setGeneratedFields(statement, model);

        } catch (SQLException exception) {
            LOGGER.error("Unable to create entity of {} class", model.getClass(), exception);
        }
        return numberOfInsertedRows > 0;
    }

    @Override
    public T find(K key) {
        String selectSql = "SELECT * FROM " + dbTable.getName() + " WHERE " + dbTable.getKey() + "=?";
        LOGGER.info("Searching for entity with sql: {}", selectSql);
        try (Connection connection = DbManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(selectSql)) {

            statement.setObject(1, key);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return entityParser.parseToEntity(entityClass, result);
            }
        } catch (SQLException exception) {
            LOGGER.error("Unable to find", exception);
        }
        return null;
    }

    @Override
    public boolean update(T model) {
        int numberOfUpdatedRows = 0;
        LOGGER.info("Updating an entity");
        try (Connection connection = DbManager.getInstance().getConnection();
             PreparedStatement statement = entityProcessor.prepareUpdateStatement(connection, model)) {

            numberOfUpdatedRows = statement.executeUpdate();

        } catch (SQLException exception) {
            LOGGER.error("Unable to update", exception);
        }
        return numberOfUpdatedRows > 0;
    }

    @Override
    public boolean delete(K key) {
        String deleteSql = "DELETE FROM " + dbTable.getName() + " WHERE " + dbTable.getKey() + " = " + key;
        LOGGER.info("Deleting an entity with sql: {}", deleteSql);
        int numberOfDeletedRows = 0;
        try (Connection connection = DbManager.getInstance().getConnection();
             Statement statement = connection.createStatement()) {

            numberOfDeletedRows = statement.executeUpdate(deleteSql);
        } catch (SQLException exception) {
            LOGGER.error("Unable to delete", exception);
        }
        return numberOfDeletedRows > 0;
    }

    @Override
    public List<T> findAll() {
        List<T> collection = new ArrayList<>();
        String selectSql = "SELECT * FROM " + dbTable.getName() + " ORDER BY " + dbTable.getKey();
        LOGGER.info("Executing SELECT query for {} table", dbTable.getName());
        try (Connection connection = DbManager.getInstance().getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet result = statement.executeQuery(selectSql);
            while (result.next()) {
                collection.add(entityParser.parseToEntity(entityClass, result));
            }
        } catch (SQLException exception) {
            LOGGER.error("Unable to select", exception);
        }
        return collection;
    }

    @Override
    public int getRecordsCount() {
        return getRecordsCountBySql(
            "SELECT COUNT(" + dbTable.getKey() + ") AS count FROM " + dbTable.getName(),
                "count");

    }

    protected int getRecordsCountBySql(String sql, String resultColumn) {
        LOGGER.info("Getting records count from {} table with sql: {}", dbTable.getName(), sql);
        try (Connection connection = DbManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getInt(resultColumn);
            }
        } catch (SQLException exception) {
            LOGGER.error("Unable to fetch records count");
        }
        return 0;
    }

    protected void setGeneratedFields(Statement statement, Object entity) throws SQLException {
        try (ResultSet resultSet = statement.getGeneratedKeys()) {
            if (resultSet.next()) {
                entityProcessor.setEntityGeneratedFields(entity, resultSet);
            }
        }
    }
}
