package com.conferences.dao.abstraction;

import com.conferences.config.DbManager;
import com.conferences.model.DbTable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * {@inheritDoc}
 */
public abstract class AbstractCrudDao<K, T> extends AbstractDao<K, T> implements ICrudDao<K, T> {

    private static final Logger LOGGER = LogManager.getLogger(AbstractCrudDao.class);

    private final Class<T> entityClass;

    protected final DbTable dbTable;

    protected AbstractCrudDao() {
        entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];

        dbTable = entityProcessor.getDbTableData(entityClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean create(T entity) {
        int numberOfInsertedRows = 0;
        LOGGER.info("Creating an entity of {} class", entity.getClass());
        try (Connection connection = DbManager.getInstance().getConnection();
             PreparedStatement statement = entityProcessor.prepareInsertStatement(connection, entity)) {

            numberOfInsertedRows = statement.executeUpdate();

            setGeneratedFields(statement, entity);

        } catch (SQLException exception) {
            LOGGER.error("Unable to create entity of {} class", entity.getClass(), exception);
        }
        return numberOfInsertedRows > 0;
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(T entity) {
        int numberOfUpdatedRows = 0;
        LOGGER.info("Updating an entity");
        try (Connection connection = DbManager.getInstance().getConnection();
             PreparedStatement statement = entityProcessor.prepareUpdateStatement(connection, entity)) {

            numberOfUpdatedRows = statement.executeUpdate();

        } catch (SQLException exception) {
            LOGGER.error("Unable to update", exception);
        }
        return numberOfUpdatedRows > 0;
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRecordsCount() {
        return getRecordsCountBySql(
            "SELECT COUNT(" + dbTable.getKey() + ") AS count FROM " + dbTable.getName(),
                "count");

    }

    /**
     * <p>
     *     Gets records count retrieved by SELECT SQL query
     * </p>
     * @param sql SELECT SQL query
     * @param resultColumn column which contains records count
     * @return number representing count of records stored in table
     */
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

    /**
     * <p>
     *     Sets values for database generated fields
     * </p>
     * @param statement statement to get generated fields from
     * @param entity entity to assign generated fields for
     * @throws SQLException an exception may occur during fetching generated fields
     */
    protected void setGeneratedFields(Statement statement, Object entity) throws SQLException {
        try (ResultSet resultSet = statement.getGeneratedKeys()) {
            if (resultSet.next()) {
                entityProcessor.setEntityGeneratedFields(entity, resultSet);
            }
        }
    }
}
