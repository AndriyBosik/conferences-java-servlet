package com.conferences.dao.implementation;

import com.conferences.config.DbManager;
import com.conferences.dao.abstraction.AbstractCrudDao;
import com.conferences.dao.abstraction.IRoleDao;
import com.conferences.entity.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleDao extends AbstractCrudDao<Integer, Role> implements IRoleDao {

    private static final Logger LOGGER = LogManager.getLogger(RoleDao.class);

    @Override
    public Role findByTitle(String title) {
        String sql = "SELECT * FROM roles WHERE title=?";
        try (Connection connection = DbManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, title.toLowerCase());

            LOGGER.info("Searching for role by title. Sql: {}", sql);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                LOGGER.info("Parsing Role");
                return entityParser.parseToEntity(Role.class, result);
            }

        } catch (SQLException exception) {
            LOGGER.error("Unable to find", exception);
        }
        return null;
    }
}
