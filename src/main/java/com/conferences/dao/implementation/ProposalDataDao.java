package com.conferences.dao.implementation;

import com.conferences.config.DbManager;
import com.conferences.dao.abstraction.AbstractDao;
import com.conferences.dao.abstraction.IProposalDataDao;
import com.conferences.entity.custom.ProposalData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProposalDataDao extends AbstractDao<Integer, ProposalData> implements IProposalDataDao {

    private static final Logger LOGGER = LogManager.getLogger(ProposalDataDao.class);
    private static final String MODERATOR_PROPOSALS_QUERY =
            "SELECT " +
                "mp.id AS id," +
                "meetings.id AS meeting_id," +
                "meetings.title AS meeting_title," +
                "meetings.date AS meeting_date," +
                "meetings.image_path AS meeting_image_path," +
                "rt.id AS report_topic_id," +
                "rt.title AS report_topic_title," +
                "mp.speaker_id AS speaker_id " +
            "FROM meetings " +
                "LEFT JOIN report_topics rt ON rt.meeting_id=meetings.id " +
                "LEFT JOIN moderator_proposals mp ON mp.report_topic_id=rt.id " +
            "WHERE mp.speaker_id=? AND meetings.date > current_timestamp AND NOT EXISTS (" +
                "SELECT NULL FROM report_topics_speakers rts WHERE rts.report_topic_id=rt.id)" +
            "ORDER BY meetings.id, rt.id";

    private static final String SPEAKER_PROPOSALS_QUERY =
            "SELECT " +
                "sp.id AS id," +
                "meetings.id AS meeting_id," +
                "meetings.title AS meeting_title," +
                "meetings.date AS meeting_date," +
                "meetings.image_path AS meeting_image_path," +
                "rt.id AS report_topic_id," +
                "rt.title AS report_topic_title," +
                "sp.speaker_id AS speaker_id " +
            "FROM meetings " +
                "LEFT JOIN report_topics rt ON rt.meeting_id=meetings.id " +
                "LEFT JOIN speaker_proposals sp ON sp.report_topic_id=rt.id " +
            "WHERE sp.speaker_id=? AND meetings.date > current_timestamp AND NOT EXISTS (" +
                "SELECT NULL FROM report_topics_speakers rts WHERE rts.report_topic_id=rt.id)" +
            "ORDER BY meetings.id, rt.id";

    @Override
    public List<ProposalData> findActiveModeratorProposalsForSpeakerOrderByMeeting(int speakerId) {
        return findProposalsBySqlAndSpeakerId(MODERATOR_PROPOSALS_QUERY, speakerId);
    }

    @Override
    public List<ProposalData> findActiveSpeakerProposalsOrderByMeeting(int speakerId) {
        return findProposalsBySqlAndSpeakerId(SPEAKER_PROPOSALS_QUERY, speakerId);
    }

    private List<ProposalData> findProposalsBySqlAndSpeakerId(String sql, int speakerId) {
        List<ProposalData> proposals = new ArrayList<>();
        try (Connection connection = DbManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, speakerId);
            LOGGER.info("Searching for proposals by speaker id. Sql: {}", sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                LOGGER.info("Parsing ProposalData");
                proposals.add(entityParser.parseToEntity(ProposalData.class, result));
            }

        } catch (SQLException exception) {
            LOGGER.error("Unable to find", exception);
            proposals.clear();
        }
        return proposals;
    }
}
