package com.conferences.dao.implementation;

import com.conferences.config.DbManager;
import com.conferences.dao.abstraction.AbstractDao;
import com.conferences.dao.abstraction.IMeetingDao;
import com.conferences.entity.Meeting;
import com.conferences.entity.ReportTopic;
import com.conferences.entity.User;
import com.conferences.util.reflection.EntityParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MeetingDao extends AbstractDao<Integer, Meeting> implements IMeetingDao {
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String DATE = "date";

    @Override
    protected PreparedStatement getInsertStatement(Connection connection, Meeting model) throws SQLException {
        return null;
    }

    @Override
    protected PreparedStatement getUpdateStatement(Connection connection, Integer key, Meeting model) throws SQLException {
        return null;
    }

    @Override
    public Meeting findByKeyWithReportTopicsAndSpeakers(Integer key) {
        String sql = "SELECT " +
                dbTable.getName() + ".*," +
                "rt.id AS report_topic_id," +
                "rt.title AS report_topic_title," +
                "rt.meeting_id AS report_topic_meeting_id," +
                "rt.speaker_id AS report_topic_speaker_id," +
                "u.id AS user_id," +
                "u.surname AS user_surname," +
                "u.name AS user_name," +
                "u.login AS user_login," +
                "u.password AS user_password," +
                "u.role_id AS user_role_id " +
                "FROM " + dbTable.getName() + " LEFT JOIN report_topics rt ON " + dbTable.getName() + ".id = rt.meeting_id LEFT JOIN users u ON rt.speaker_id=u.id WHERE " + dbTable.getName() + ".id=?";
        try (Connection connection = DbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, key);

            ResultSet result = statement.executeQuery();

            Meeting meeting = null;
            while (result.next()) {
                if (meeting == null) {
                    meeting = EntityParser.parseToEntity(Meeting.class, result);
                }
                if (result.getInt("report_topic_id") != 0) {
                    ReportTopic reportTopic = EntityParser.parseToEntity(ReportTopic.class, result, "report_topic_");
                    reportTopic.setSpeaker(EntityParser.parseToEntity(User.class, result, "user_"));
                    meeting.getReportTopics().add(reportTopic);
                }
            }
            return meeting;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
