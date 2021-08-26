package com.conferences.dao.implementation;

import com.conferences.config.DbManager;
import com.conferences.dao.abstraction.AbstractDao;
import com.conferences.dao.abstraction.IMeetingDao;
import com.conferences.entity.Meeting;
import com.conferences.entity.ReportTopic;
import com.conferences.entity.User;
import com.conferences.parser.IParser;
import com.conferences.parser.MeetingParser;
import com.conferences.parser.ReportTopicParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MeetingDao extends AbstractDao<Integer, Meeting> implements IMeetingDao {
    private static final String TABLE_NAME = "meetings";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String DATE = "date";

    private IParser<Meeting> meetingParser;
    private IParser<ReportTopic> reportTopicParser;

    public MeetingDao() {
        meetingParser = new MeetingParser();
        reportTopicParser = new ReportTopicParser();
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
    protected IParser<Meeting> getParser() {
        return meetingParser;
    }

    @Override
    protected PreparedStatement getInsertStatement(Connection connection, Meeting model) throws SQLException {
        return null;
    }

    @Override
    protected PreparedStatement getUpdateStatement(Connection connection, Integer key, Meeting model) throws SQLException {
        return null;
    }

    @Override
    public Meeting findByKeyWithReportTopics(Integer key) {
        String sql = "SELECT meetings.*, rt.id AS report_topic_id, rt.title AS report_topic_title, rt.meeting_id AS report_topic_meeting_id FROM " + TABLE_NAME + " LEFT JOIN report_topics rt ON meetings.id = rt.meeting_id WHERE meetings.id=?";
        try (Connection connection = DbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, key);

            ResultSet result = statement.executeQuery();

            Meeting meeting = null;
            while (result.next()) {
                if (meeting == null) {
                    meeting = meetingParser.parseToModel(result);
                }
                if (result.getInt("report_topic_id") != 0) {
                    meeting.getReportTopics().add(reportTopicParser.parseToModel(result, "report_topic_"));
                }
            }
            return meeting;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
