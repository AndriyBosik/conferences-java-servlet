package com.conferences.dao.implementation;

import com.conferences.dao.abstraction.AbstractDao;
import com.conferences.dao.abstraction.IRoleDao;
import com.conferences.entity.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RoleDao extends AbstractDao<Integer, Role> implements IRoleDao {
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String TABLE_NAME = "roles";

    @Override
    protected PreparedStatement getInsertStatement(Connection connection, Role model) throws SQLException {
        String sql = "INSERT INTO " + TABLE_NAME + "(" + TITLE + ") VALUES(?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, model.getTitle());
        return statement;
    }

    @Override
    protected PreparedStatement getUpdateStatement(Connection connection, Integer key, Role model) throws SQLException {
        String sql = "UPDATE " + TABLE_NAME + " SET " + TITLE + "=? WHERE " + dbTable.getKey() + "=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, model.getTitle());
        statement.setInt(2, key);
        return statement;
    }
}
