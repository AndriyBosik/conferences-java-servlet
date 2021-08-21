package com.conferences.dao.abstraction;

import com.conferences.config.DbManager;
import com.conferences.parser.IParser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDao<K, T> implements IDao<K, T> {

    protected abstract String getTableName();
    protected abstract String getKeyName();

    protected abstract IParser<T> getParser();

    protected abstract PreparedStatement getInsertStatement(Connection connection, T model) throws SQLException;
    protected abstract PreparedStatement getUpdateStatement(Connection connection, K key, T model) throws SQLException;

    @Override
    public boolean create(T model) {
        int numberOfInsertedRows = 0;
        try (Connection connection = DbManager.getConnection();
             PreparedStatement statement = getInsertStatement(connection, model)) {
            numberOfInsertedRows = statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return numberOfInsertedRows > 0;
    }

    @Override
    public T find(K key) {
        String selectSql = "SELECT * FROM " + getTableName() + " WHERE " + getKeyName() + " = " + key;
        try (Connection connection = DbManager.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet result = statement.executeQuery(selectSql);
            if (result.next()) {
                return getParser().parseToModel(result);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(K key, T model) {
        int numberOfUpdatedRows = 0;
        try (Connection connection = DbManager.getConnection();
             PreparedStatement statement = getUpdateStatement(connection, key, model)) {

            numberOfUpdatedRows = statement.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return numberOfUpdatedRows > 0;
    }

    @Override
    public boolean delete(K key) {
        String deleteSql = "DELETE * FROM " + getTableName() + " WHERE " + getKeyName() + " = " + key;
        int numberOfDeletedRows = 0;
        try (Connection connection = DbManager.getConnection();
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
        String selectSql = "SELECT * FROM " + getTableName();
        try (Connection connection = DbManager.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet result = statement.executeQuery(selectSql);
            while (result.next()) {
                collection.add(getParser().parseToModel(result));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return collection;
    }
}
