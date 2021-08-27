package com.conferences.dao.implementation;

import com.conferences.config.DbManager;
import com.conferences.dao.abstraction.AbstractDao;
import com.conferences.dao.abstraction.IMeetingDao;
import com.conferences.entity.Meeting;
import com.conferences.entity.ReportTopic;
import com.conferences.entity.User;
import com.conferences.model.Page;
import com.conferences.model.PageResponse;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MeetingDao extends AbstractDao<Integer, Meeting> implements IMeetingDao {
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String DATE = "date";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE_PATH = "image_path";

    @Override
    protected PreparedStatement getInsertStatement(Connection connection, Meeting model) throws SQLException {
        String sql = "INSERT INTO " + dbTable.getName() + "(" + TITLE + ", " + DATE + ", " + DESCRIPTION + ", " + IMAGE_PATH + ") VALUES(?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql, new String[]{ dbTable.getKey() });
        statement.setString(1, model.getTitle());
        statement.setTimestamp(2, new Timestamp(model.getDate().getTime()));
        statement.setString(3, model.getDescription());
        statement.setString(4, model.getImagePath());
        return statement;
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

    @Override
    public PageResponse<List<Meeting>> findAllPage(Page page) {
        final int offset = (page.getPageNumber() - 1)*page.getItemsCount();
        String sql = "SELECT * FROM " + dbTable.getName() + " ORDER BY " + dbTable.getKey() + " OFFSET ? LIMIT ?";

        PageResponse<List<Meeting>> answer = new PageResponse<>();

        try (Connection connection = DbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, offset);
            statement.setInt(2, page.getItemsCount());

            ResultSet result = statement.executeQuery();
            List<Meeting> meetings = new ArrayList<>();
            while (result.next()) {
                meetings.add(entityParser.parseToEntity(Meeting.class, result));
            }

            answer.setItem(meetings);

            int totalRecords = getRecordsCount();
            answer.setPagesCount(
                totalRecords / page.getItemsCount() +
                (totalRecords % page.getItemsCount() > 0 ? 1 : 0)
            );

            return answer;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
