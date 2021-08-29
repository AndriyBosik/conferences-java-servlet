package com.conferences.dao.implementation;

import com.conferences.config.DbManager;
import com.conferences.dao.abstraction.AbstractDao;
import com.conferences.dao.abstraction.IUserMeetingDao;
import com.conferences.entity.UserMeeting;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMeetingDao extends AbstractDao<Integer, UserMeeting> implements IUserMeetingDao {

    @Override
    public UserMeeting findByUserIdAndMeetingId(int userId, int meetingId) {
        String sql = "SELECT * FROM " + dbTable.getName() + " WHERE user_id=? AND meeting_id=?";
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
}
