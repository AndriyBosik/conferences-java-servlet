package com.conferences.dao.implementation;

import com.conferences.config.DbManager;
import com.conferences.dao.abstraction.AbstractDao;
import com.conferences.dao.abstraction.IUserDao;
import com.conferences.entity.Role;
import com.conferences.entity.User;

import java.sql.*;

public class UserDao extends AbstractDao<Integer, User> implements IUserDao {
    private static final String ID = "id";
    private static final String ROLE_ID = "role_id";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";

    @Override
    public User findByLoginAndPasswordWithRole(String login, String password) {
        String sql = "SELECT " + dbTable.getName() + ".*," +
                entityProcessor.getEntityFieldsWithPrefixes(Role.class, "r.", "role_") + " " +
                "FROM " + dbTable.getName() + " LEFT JOIN roles r on r.id=" + dbTable.getName() + ".role_id WHERE " + dbTable.getName() + ".login=? AND " + dbTable.getName() + ".password=?";
        try (Connection connection = DbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, login);
            statement.setString(2, password);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                User user = entityParser.parseToEntity(User.class, result);
                user.setRole(entityParser.parseToEntity(Role.class, result, "role_"));
                return user;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
