package com.conferences.dao.implementation;

import com.conferences.config.DbManager;
import com.conferences.dao.abstraction.AbstractDao;
import com.conferences.dao.abstraction.IUserMeetingDao;
import com.conferences.entity.ReportTopic;
import com.conferences.entity.User;
import com.conferences.entity.UserMeeting;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserMeetingDao extends AbstractDao<Integer, UserMeeting> implements IUserMeetingDao {

    @Override
    public UserMeeting findByUserIdAndMeetingId(int userId, int meetingId) {
        String sql = "SELECT * FROM users_meetings WHERE user_id=? AND meeting_id=?";
        try (Connection connection = DbManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            statement.setInt(2, meetingId);

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return entityParser.parseToEntity(UserMeeting.class, result);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public List<UserMeeting> findUserWithPresenceByMeetingId(int meetingId) {
        String sql = "SELECT " +
                entityProcessor.getEntityFieldsWithPrefixes(User.class, "u.", "user_") + "," +
                "um.* FROM users u " +
                "LEFT JOIN users_meetings um ON um.user_id=u.id " +
            "WHERE um.meeting_id=? " +
            "ORDER BY um.id";
        List<UserMeeting> userMeetings = new ArrayList<>();
        try (Connection connection = DbManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, meetingId);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                UserMeeting userMeeting = entityParser.parseToEntity(UserMeeting.class, result);
                User user = entityParser.parseToEntity(User.class, result, "user_");
                userMeeting.setUser(user);
                userMeetings.add(userMeeting);
            }
        } catch (SQLException exception) {
            userMeetings.clear();
            exception.printStackTrace();
        }
        return userMeetings;
    }

    @Override
    public boolean updateUserPresenceByUserIdAndMeetingId(UserMeeting userMeeting) {
        String sql = "UPDATE users_meetings SET present=? WHERE user_id=? AND meeting_id=?";
        try (Connection connection = DbManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setBoolean(1, userMeeting.isPresent());
            statement.setInt(2, userMeeting.getUserId());
            statement.setInt(3, userMeeting.getMeetingId());
            statement.executeUpdate();

            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }
}
