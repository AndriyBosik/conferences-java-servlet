package com.conferences.dao.implementation;

import com.conferences.config.DbManager;
import com.conferences.dao.abstraction.AbstractCrudDao;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@inheritDoc}
 */
public class MeetingDao extends AbstractCrudDao<Integer, Meeting> implements IMeetingDao {

    private static final Logger LOGGER = LogManager.getLogger(MeetingDao.class);

    /**
     * {@inheritDoc}
     */
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
                    "LEFT JOIN users u ON u.id=rts.speaker_id " +
                "WHERE meetings.id=? " +
                "GROUP BY meetings.id, rt.id, rts.id, u.id " +
                "ORDER BY meetings.id, rt.id";
        LOGGER.info("Searching for meeting. Sql query: {}", sql);
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
        } catch (SQLException exception) {
            LOGGER.error("Error during query executing", exception);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<Meeting> findAllPageBySorterWithUsersCountAndTopicsCount(Page page, MeetingSorter sorter) {
        return findAllPageBySorterWithUsersCountAndTopicsCountAndFilter(page, sorter, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<Meeting> findAllSpeakerMeetingsPageBySorterWithUsersCountAndTopicsCount(Page page, MeetingSorter sorter, int speakerId) {
        final String OPEN_EXISTS = "EXISTS (";
        final String CLOSE_EXISTS = ")";
        String filterCondition =
            OPEN_EXISTS +
                "SELECT 1 FROM users WHERE id=" + speakerId + " AND " +
                    OPEN_EXISTS +
                        "SELECT 1 FROM report_topics_speakers rts WHERE rts.speaker_id=" + speakerId + " AND " +
                            OPEN_EXISTS +
                                "SELECT 1 FROM report_topics rt WHERE rt.meeting_id=meetings.id AND rt.id=rts.report_topic_id" +
                            CLOSE_EXISTS +
                    CLOSE_EXISTS +
            CLOSE_EXISTS;
        return findAllPageBySorterWithUsersCountAndTopicsCountAndFilter(page, sorter, filterCondition);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateMeetingEditableData(Meeting meeting) {
        String sql = "UPDATE meetings SET address=?, date=? WHERE id=?";
        LOGGER.info("Updating meeting with sql: {}", sql);
        try (Connection connection = DbManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, meeting.getAddress());
            statement.setTimestamp(2, Timestamp.valueOf(meeting.getDate()));
            statement.setInt(3, meeting.getId());
            statement.executeUpdate();

            return true;
        } catch (SQLException exception) {
            LOGGER.error("Exception during updating", exception);
        }
        return false;
    }

    /**
     * <p>
     *     Retrieves sorted and filtered meetings with users count and topics count with custom SQL filter condition
     * </p>
     * @param page model which contains data about page
     * @param sorter model which contains sort and filter options
     * @param filterCondition SQL condition to filter by
     * @return {@link PageResponse} which contains list of {@link Meeting} objects and other page data
     */
    private PageResponse<Meeting> findAllPageBySorterWithUsersCountAndTopicsCountAndFilter(Page page, MeetingSorter sorter, String filterCondition) {
        PageResponse<Meeting> pageResponse = new PageResponse<>();
        pageResponse.setPageSize(page.getItemsCount());
        final int offset = (page.getPageNumber() - 1)*page.getItemsCount();

        IQueryBuilder queryBuilder = MeetingSorterQueryBuilderFactory.getInstance().getQueryBuilder(sorter, getMeetingPageColumns());
        queryBuilder = MeetingSelectorQueryBuilderFactory.getInstance().getDecorator(queryBuilder, sorter.getFilterSelector());

        int itemsCount = getItemsCount(queryBuilder, filterCondition);
        pageResponse.setTotalItems(itemsCount);

        queryBuilder = new MeetingPageQueryBuilderDecorator(queryBuilder);

        String sql = buildMeetingPageSqlQuery(queryBuilder, filterCondition);

        LOGGER.info("Searching for meetings for specific page. Sql: {}", sql);
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
                meeting.setPresentUsersCount(result.getInt("present_users_count"));
                meetings.add(meeting);
            }
        } catch (SQLException exception) {
            LOGGER.error("Unable to get page", exception);
        }
        pageResponse.setItems(meetings);
        return pageResponse;
    }

    /**
     * @return returns map which contains information about column names and prefixes
     */
    private Map<String, String> getMeetingPageColumns() {
        Map<String, String> columns = new HashMap<>();
        columns.put(MeetingSorterQueryBuilderFactory.MEETINGS_KEY, "meetings.");
        columns.put(MeetingSorterQueryBuilderFactory.TOPICS_COUNT_KEY, "topics_count");
        columns.put(MeetingSorterQueryBuilderFactory.USERS_COUNT_KEY, "users_count");
        return columns;
    }

    /**
     * <p>
     *     Builds SQL query to execute and retrieve items count from
     * </p>
     * @param queryBuilder builder used to generate SQL query
     * @param filterCondition SQL condition to filter by
     * @return number representing count of retrieved items
     */
    private int getItemsCount(IQueryBuilder queryBuilder, String filterCondition) {
        String sql = queryBuilder
                .select("COUNT(meetings.id) AS meetings_count")
                .from("meetings")
                .where(filterCondition)
                .generateQuery();
        LOGGER.info("Getting count of meetings: {}", sql);
        return getRecordsCountBySql(sql, "meetings_count");
    }

    /**
     * <p>
     *     Uses {@link IQueryBuilder} to build SQL query to get list of {@link Meeting} objects for specified page
     * </p>
     * @param queryBuilder builder used to build SQL query
     * @param filterCondition additional SQL condition to filter by
     * @return SQL query
     */
    private String buildMeetingPageSqlQuery(IQueryBuilder queryBuilder, String filterCondition) {
        return queryBuilder
                .select(
                        "meetings.*",
                        "COALESCE(stats.users_count, 0) AS users_count",
                        "COALESCE(stats.present_users_count, 0) AS present_users_count",
                        "(SELECT COUNT(id) FROM report_topics WHERE meeting_id=meetings.id) AS topics_count")
                .from("meetings LEFT JOIN (" +
                    "SELECT " +
                        "meeting_id," +
                        "COUNT(id) AS users_count," +
                        "SUM(present::int) AS present_users_count " +
                    "FROM users_meetings GROUP BY meeting_id" +
                ") AS stats ON stats.meeting_id=meetings.id")
                .where(filterCondition)
                .groupBy("meetings.id", "stats.users_count", "stats.present_users_count")
                .orderBy("meetings.id")
                .generateQuery();
    }
}
