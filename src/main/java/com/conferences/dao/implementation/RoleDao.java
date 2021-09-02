package com.conferences.dao.implementation;

import com.conferences.config.DbManager;
import com.conferences.dao.abstraction.AbstractCrudDao;
import com.conferences.dao.abstraction.IRoleDao;
import com.conferences.entity.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleDao extends AbstractCrudDao<Integer, Role> implements IRoleDao {

    @Override
    public Role findByTitle(String title) {
        String sql = "SELECT * FROM roles WHERE title=?";
        try (Connection connection = DbManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, title.toLowerCase());

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return entityParser.parseToEntity(Role.class, result);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
