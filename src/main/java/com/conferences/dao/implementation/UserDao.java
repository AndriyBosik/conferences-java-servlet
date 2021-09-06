package com.conferences.dao.implementation;

import com.conferences.config.DbManager;
import com.conferences.dao.abstraction.AbstractCrudDao;
import com.conferences.dao.abstraction.IUserDao;
import com.conferences.entity.Role;
import com.conferences.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends AbstractCrudDao<Integer, User> implements IUserDao {

    private static final Logger LOGGER = LogManager.getLogger(UserDao.class);

    @Override
    public User findByLoginWithRole(String login, String password) {
        String sql = "SELECT users.*," +
                entityProcessor.getEntityFieldsWithPrefixes(Role.class, "r.", "role_") + " " +
                "FROM users LEFT JOIN roles r ON r.id=users.role_id WHERE users.login=?";
        LOGGER.info("Selecting user with role by login. Sql: {}", sql);
        try (Connection connection = DbManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, login);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                LOGGER.info("Parsing user");
                User user = entityParser.parseToEntity(User.class, result);
                user.setRole(entityParser.parseToEntity(Role.class, result, "role_"));
                return user;
            }
        } catch (SQLException ex) {
            LOGGER.error("Unable to select");
        }
        return null;
    }

    @Override
    public User findByLoginOrEmail(String login, String email) {
        String sql = "SELECT * FROM users WHERE login=? OR email=?";
        try (Connection connection = DbManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, login);
            statement.setString(2, email);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return entityParser.parseToEntity(User.class, result);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    @Override
    public List<User> findAllByRole(String role) {
        String sql = "SELECT users.*," +
                entityProcessor.getEntityFieldsWithPrefixes(Role.class, "r.", "role_") + " " +
                "FROM users LEFT JOIN roles r ON r.id=users.role_id WHERE r.title=?";

        List<User> users = new ArrayList<>();

        try (Connection connection = DbManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, role);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                User user = entityParser.parseToEntity(User.class, result);
                user.setRole(entityParser.parseToEntity(Role.class, result, "role_"));
                users.add(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return users;
    }

    @Override
    public List<User> findAvailableSpeakersForProposalByTopic(int topicId) {
        String sql = "SELECT users.* FROM users WHERE NOT EXISTS " +
                "(SELECT NULL FROM moderator_proposals mp WHERE mp.speaker_id=users.id AND mp.report_topic_id=?)" +
                "AND EXISTS" +
                "(SELECT NULL FROM roles r WHERE r.title='speaker' AND r.id=users.role_id)";
        List<User> speakers = new ArrayList<>();
        try (Connection connection = DbManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, topicId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                speakers.add(entityParser.parseToEntity(User.class, resultSet));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return speakers;
    }

    @Override
    public boolean updateUserImagePath(User user) {
        String sql = "UPDATE users SET image_path=? WHERE id=?";
        try (Connection connection = DbManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getImagePath());
            statement.setInt(2, user.getId());
            statement.executeUpdate();

            return true;
        } catch (SQLException exception) {

        }
        return false;
    }

}
