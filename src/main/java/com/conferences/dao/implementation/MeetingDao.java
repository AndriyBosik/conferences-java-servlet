package com.conferences.dao.implementation;

import com.conferences.config.DbManager;
import com.conferences.dao.abstraction.AbstractDao;
import com.conferences.dao.abstraction.IMeetingDao;
import com.conferences.entity.Meeting;
import com.conferences.entity.ReportTopic;
import com.conferences.entity.ReportTopicSpeaker;
import com.conferences.entity.User;
import com.conferences.model.Page;
import com.conferences.model.PageResponse;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MeetingDao extends AbstractDao<Integer, Meeting> implements IMeetingDao {

    @Override
    public Meeting findByKeyWithReportTopicsAndSpeakersAndUsersCount(Integer key) {
        String sql =
                "SELECT " +
                    "meetings.*," +
                    "COUNT(DISTINCT um.id) AS users_count," +
                    entityProcessor.getEntityFieldsWithPrefixes(ReportTopic.class, "rt.", "report_topic_") + "," +
                    entityProcessor.getEntityFieldsWithPrefixes(ReportTopicSpeaker.class, "rts.", "report_topic_speaker_") + "," +
                    entityProcessor.getEntityFieldsWithPrefixes(User.class, "u.", "user_") + " " +
                "FROM meetings " +
                    "LEFT JOIN report_topics rt ON meetings.id=rt.meeting_id " +
                    "LEFT JOIN users_meetings um ON um.meeting_id=meetings.id " +
                    "LEFT JOIN report_topics_speakers rts ON rts.report_topic_id=rt.id " +
                "   LEFT JOIN users u ON u.id=rts.speaker_id " +
                "WHERE meetings.id=? " +
                "GROUP BY meetings.id, rt.id, rts.id, u.id " +
                "ORDER BY meetings.id, rt.id";
        try (Connection connection = DbManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, key);

            ResultSet result = statement.executeQuery();

            Meeting meeting = null;
            List<ReportTopic> reportTopics = new ArrayList<>();
            while (result.next()) {
                if (meeting == null) {
                    meeting = entityParser.parseToEntity(Meeting.class, result);
                    meeting.setUsersCount(result.getInt("users_count"));
                }
                ReportTopic reportTopic = entityParser.parseToEntity(ReportTopic.class, result, "report_topic_");
                if (reportTopic != null) {
                    ReportTopicSpeaker reportTopicSpeaker = entityParser.parseToEntity(ReportTopicSpeaker.class, result, "report_topic_speaker_");
                    if (reportTopicSpeaker != null) {
                        User speaker = entityParser.parseToEntity(User.class, result, "user_");
                        reportTopicSpeaker.setSpeaker(speaker);
                    }
                    reportTopic.setReportTopicSpeaker(reportTopicSpeaker);
                    reportTopics.add(reportTopic);
                }
                meeting.setReportTopics(reportTopics);
            }
            return meeting;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Meeting> findAllPageWithUsersCountAndTopicsCount(Page page) {
        final int offset = (page.getPageNumber() - 1)*page.getItemsCount();
        String sql =
                "SELECT " +
                    "meetings.*," +
                    "COUNT (DISTINCT rt.id) AS topics_count," +
                    "COUNT (DISTINCT um.id) AS users_count " +
                "FROM meetings " +
                    "LEFT JOIN report_topics rt ON rt.meeting_id=meetings.id " +
                    "LEFT JOIN users_meetings um ON um.meeting_id=meetings.id " +
                "GROUP BY meetings.id " +
                "ORDER BY meetings.id OFFSET ? LIMIT ?";

        try (Connection connection = DbManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, offset);
            statement.setInt(2, page.getItemsCount());

            ResultSet result = statement.executeQuery();
            List<Meeting> meetings = new ArrayList<>();
            while (result.next()) {
                Meeting meeting = entityParser.parseToEntity(Meeting.class, result);
                meeting.setReportTopicsCount(result.getInt("topics_count"));
                meeting.setUsersCount(result.getInt("users_count"));
                meetings.add(meeting);
            }

            return meetings;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
