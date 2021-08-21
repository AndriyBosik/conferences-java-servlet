package com.conferences.dao.implementation;

import com.conferences.config.DbManager;
import com.conferences.dao.abstraction.AbstractDao;
import com.conferences.dao.abstraction.IUserDao;
import com.conferences.model.Role;
import com.conferences.model.User;
import com.conferences.parser.IParser;
import com.conferences.parser.RoleParser;
import com.conferences.parser.UserParser;

import java.sql.*;

public class UserDao extends AbstractDao<Integer, User> implements IUserDao {
    private static final String TABLE_NAME = "users";
    private static final String ID = "id";
    private static final String ROLE_ID = "role_id";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";

    private IParser<User> userParser;
    private IParser<Role> roleParser;

    public UserDao() {
        userParser = new UserParser();
        roleParser = new RoleParser();
    }

    @Override
    public User findByLoginAndPasswordWithRole(String login, String password) {
        String sql = "SELECT users.*, r.id AS roles_id, r.title AS roles_title FROM " + TABLE_NAME + " LEFT JOIN roles r on r.id=users.role_id WHERE users.login=? AND users.password=?";
        try (Connection connection = DbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, login);
            statement.setString(2, password);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                User user = userParser.parseToModel(result);
                user.setRole(roleParser.parseToModel(result, "roles_"));
                return user;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected String getKeyName() {
        return ID;
    }

    @Override
    protected IParser<User> getParser() {
        return userParser;
    }

    @Override
    protected PreparedStatement getInsertStatement(Connection connection, User model) throws SQLException {
        String sql = "INSERT INTO " + TABLE_NAME + "(" + LOGIN + ", " + PASSWORD + ", " + ROLE_ID + ") VALUES(?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, model.getLogin());
        statement.setString(2, model.getPassword());
        statement.setInt(3, model.getRoleId());
        return statement;
    }

    @Override
    protected PreparedStatement getUpdateStatement(Connection connection, Integer key, User model) throws SQLException {
        String sql = "UPDATE " + TABLE_NAME + " SET " + LOGIN + "=?, " + PASSWORD + "=?, " + ROLE_ID + "=? WHERE " + getKeyName() + "=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, model.getLogin());
        statement.setString(2, model.getPassword());
        statement.setInt(3, model.getRoleId());
        statement.setInt(4, key);
        return statement;
    }

}
