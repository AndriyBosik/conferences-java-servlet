package com.conferences.dao.abstraction;

import com.conferences.config.DbManager;
import com.conferences.handler.TransactionHandler;
import com.conferences.model.DbTable;
import com.conferences.util.reflection.abstraction.IEntityProcessor;
import com.conferences.util.reflection.implementation.EntityParser;
import com.conferences.util.reflection.implementation.EntityProcessor;
import com.conferences.util.reflection.abstraction.IEntityParser;

import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDao<K, T> implements IDao<K, T> {

    private final Class<T> entityClass;

    protected IEntityParser entityParser;
    protected IEntityProcessor entityProcessor;
    protected TransactionHandler transactionHandler;
    protected DbTable dbTable;

    protected AbstractDao() {
        entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];

        entityParser = new EntityParser();
        entityProcessor = new EntityProcessor();
        transactionHandler = new TransactionHandler();
        dbTable = entityProcessor.getEntityFieldsList(entityClass);
    }

    @Override
    public boolean create(T model) {
        int numberOfInsertedRows = 0;
        try (Connection connection = DbManager.getInstance().getConnection();
             PreparedStatement statement = entityProcessor.prepareInsertStatement(connection, model)) {

            numberOfInsertedRows = statement.executeUpdate();

            setGeneratedFields(statement, model);

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return numberOfInsertedRows > 0;
    }

    @Override
    public T find(K key) {
        String selectSql = "SELECT * FROM " + dbTable.getName() + " WHERE " + dbTable.getKey() + "=" + key;
        try (Connection connection = DbManager.getInstance().getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet result = statement.executeQuery(selectSql);
            if (result.next()) {
                return entityParser.parseToEntity(entityClass, result);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(T model) {
        int numberOfUpdatedRows = 0;
        try (Connection connection = DbManager.getInstance().getConnection();
             PreparedStatement statement = entityProcessor.prepareUpdateStatement(connection, model)) {

            numberOfUpdatedRows = statement.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return numberOfUpdatedRows > 0;
    }

    @Override
    public boolean delete(K key) {
        String deleteSql = "DELETE * FROM " + dbTable.getName() + " WHERE " + dbTable.getKey() + " = " + key;
        int numberOfDeletedRows = 0;
        try (Connection connection = DbManager.getInstance().getConnection();
             Statement statement = connection.createStatement()) {

            numberOfDeletedRows = statement.executeUpdate(deleteSql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return numberOfDeletedRows > 0;
    }

    @Override
    public List<T> findAll() {
        List<T> collection = new ArrayList<>();
        String selectSql = "SELECT * FROM " + dbTable.getName() + " ORDER BY " + dbTable.getKey();
        try (Connection connection = DbManager.getInstance().getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet result = statement.executeQuery(selectSql);
            while (result.next()) {
                collection.add(entityParser.parseToEntity(entityClass, result));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return collection;
    }

    @Override
    public int getRecordsCount() {
        String sql = "SELECT COUNT(" + dbTable.getKey() + ") AS count FROM " + dbTable.getName();
        try (Connection connection = DbManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getInt("count");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
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
