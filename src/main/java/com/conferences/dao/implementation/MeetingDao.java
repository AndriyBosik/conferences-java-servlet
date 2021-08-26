package com.conferences.dao.implementation;

import com.conferences.config.DbManager;
import com.conferences.dao.abstraction.AbstractDao;
import com.conferences.dao.abstraction.IMeetingDao;
import com.conferences.entity.Meeting;
import com.conferences.entity.ReportTopic;
import com.conferences.entity.User;

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
                entityProcessor.getEntityFieldsWithPrefixes(ReportTopic.class, "rt.", "report_topic_") + "," +
                entityProcessor.getEntityFieldsWithPrefixes(User.class, "u.", "user_") + " " +
                "FROM " + dbTable.getName() + " LEFT JOIN report_topics rt ON " + dbTable.getName() + ".id = rt.meeting_id LEFT JOIN users u ON rt.speaker_id=u.id " +
                "WHERE " + dbTable.getName() + ".id=? " +
                "ORDER BY rt." + entityProcessor.getEntityFieldsList(ReportTopic.class).getKey();
        try (Connection connection = DbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, key);

            ResultSet result = statement.executeQuery();

            Meeting meeting = null;
            while (result.next()) {
                if (meeting == null) {
                    meeting = entityParser.parseToEntity(Meeting.class, result);
                }
                ReportTopic reportTopic = entityParser.parseToEntity(ReportTopic.class, result, "report_topic_");
                if (reportTopic.getId() != 0) {
                    reportTopic.setSpeaker(entityParser.parseToEntity(User.class, result, "user_"));
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
