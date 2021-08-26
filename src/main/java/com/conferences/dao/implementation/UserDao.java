package com.conferences.dao.implementation;

import com.conferences.config.DbManager;
import com.conferences.dao.abstraction.AbstractDao;
import com.conferences.dao.abstraction.IUserDao;
import com.conferences.entity.Role;
import com.conferences.entity.User;
import com.conferences.util.reflection.EntityParser;

import java.sql.*;

public class UserDao extends AbstractDao<Integer, User> implements IUserDao {
    private static final String ID = "id";
    private static final String ROLE_ID = "role_id";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";

    @Override
    public User findByLoginAndPasswordWithRole(String login, String password) {
        String sql = "SELECT " + dbTable.getName() + ".*, r.id AS roles_id, r.title AS roles_title FROM " + dbTable.getName() + " LEFT JOIN roles r on r.id=" + dbTable.getName() + ".role_id WHERE " + dbTable.getName() + ".login=? AND " + dbTable.getName() + ".password=?";
        try (Connection connection = DbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, login);
            statement.setString(2, password);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                User user = EntityParser.parseToEntity(User.class, result);
                user.setRole(EntityParser.parseToEntity(Role.class, result, "roles_"));
                return user;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    protected PreparedStatement getInsertStatement(Connection connection, User model) throws SQLException {
        String sql = "INSERT INTO " + dbTable.getName() + "(" + LOGIN + ", " + PASSWORD + ", " + ROLE_ID + ") VALUES(?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, model.getLogin());
        statement.setString(2, model.getPassword());
        statement.setInt(3, model.getRoleId());
        return statement;
    }

    @Override
    protected PreparedStatement getUpdateStatement(Connection connection, Integer key, User model) throws SQLException {
        String sql = "UPDATE " + dbTable.getName() + " SET " + LOGIN + "=?, " + PASSWORD + "=?, " + ROLE_ID + "=? WHERE " + dbTable.getKey() + "=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, model.getLogin());
        statement.setString(2, model.getPassword());
        statement.setInt(3, model.getRoleId());
        statement.setInt(4, key);
        return statement;
    }

}
