package com.conferences.dao.implementation;

import com.conferences.config.DbManager;
import com.conferences.dao.abstraction.AbstractDao;
import com.conferences.dao.abstraction.IMeetingDao;
import com.conferences.decorator.MeetingPageQueryBuilderDecorator;
import com.conferences.entity.Meeting;
import com.conferences.entity.ReportTopic;
import com.conferences.entity.ReportTopicSpeaker;
import com.conferences.entity.User;
import com.conferences.factory.MeetingSelectorQueryBuilderFactory;
import com.conferences.factory.MeetingSorterQueryBuilderFactory;
import com.conferences.handler.abstraction.IQueryBuilder;
import com.conferences.model.MeetingSorter;
import com.conferences.model.Page;
import com.conferences.model.PageResponse;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public PageResponse<Meeting> findAllPageBySorterWithUsersCountAndTopicsCount(Page page, MeetingSorter sorter) {
        PageResponse<Meeting> pageResponse = new PageResponse<>();
        pageResponse.setPageSize(page.getItemsCount());
        final int offset = (page.getPageNumber() - 1)*page.getItemsCount();

        IQueryBuilder queryBuilder = MeetingSorterQueryBuilderFactory.getInstance().getQueryBuilder(sorter, getMeetingPageColumns());
        queryBuilder = MeetingSelectorQueryBuilderFactory.getInstance().getDecorator(queryBuilder, sorter.getFilterSelector());

        int itemsCount = getItemsCount(queryBuilder);
        pageResponse.setTotalItems(itemsCount);

        queryBuilder = new MeetingPageQueryBuilderDecorator(queryBuilder);

        String sql = buildMeetingPageSqlQuery(queryBuilder);

        List<Meeting> meetings = new ArrayList<>();
        try (Connection connection = DbManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, offset);
            statement.setInt(2, page.getItemsCount());

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Meeting meeting = entityParser.parseToEntity(Meeting.class, result);
                meeting.setReportTopicsCount(result.getInt("topics_count"));
                meeting.setUsersCount(result.getInt("users_count"));
                meetings.add(meeting);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        pageResponse.setItems(meetings);
        return pageResponse;
    }

    @Override
    public boolean updateMeetingEditableData(Meeting meeting) {
        String sql = "UPDATE meetings SET address=?, date=? WHERE id=?";
        try (Connection connection = DbManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, meeting.getAddress());
            statement.setTimestamp(2, new Timestamp(meeting.getDate().getTime()));
            statement.setInt(3, meeting.getId());
            statement.executeUpdate();

            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    private Map<String, String> getMeetingPageColumns() {
        Map<String, String> columns = new HashMap<>();
        columns.put(MeetingSorterQueryBuilderFactory.MEETINGS_KEY, "meetings.");
        columns.put(MeetingSorterQueryBuilderFactory.TOPICS_COUNT_KEY, "topics_count");
        columns.put(MeetingSorterQueryBuilderFactory.USERS_COUNT_KEY, "users_count");
        return columns;
    }

    private int getItemsCount(IQueryBuilder queryBuilder) {
        String sql = queryBuilder
                .select("COUNT(meetings.id) AS meetings_count")
                .from("meetings")
                .where(null)
                .generateQuery();
        return getRecordsCountBySql(sql, "meetings_count");
    }

    private String buildMeetingPageSqlQuery(IQueryBuilder queryBuilder) {
        return queryBuilder
                .select(
                        "meetings.*",
                        "COUNT (DISTINCT rt.id) AS topics_count",
                        "COUNT (DISTINCT um.id) AS users_count")
                .from("meetings")
                .leftJoin("report_topics rt", "rt.meeting_id=meetings.id")
                .leftJoin("users_meetings um", "um.meeting_id=meetings.id")
                .where(null)
                .groupBy("meetings.id")
                .orderBy("meetings.id")
                .generateQuery();
    }
}
