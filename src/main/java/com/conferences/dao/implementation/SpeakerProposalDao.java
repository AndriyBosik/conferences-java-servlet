package com.conferences.dao.implementation;

import com.conferences.config.DbManager;
import com.conferences.dao.abstraction.AbstractCrudDao;
import com.conferences.dao.abstraction.ISpeakerProposalDao;
import com.conferences.entity.SpeakerProposal;
import com.conferences.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SpeakerProposalDao extends AbstractCrudDao<Integer, SpeakerProposal> implements ISpeakerProposalDao {

    private static final Logger LOGGER = LogManager.getLogger(SpeakerProposalDao.class);

    @Override
    public List<SpeakerProposal> findAllByTopicIdWithSpeaker(int topicId) {
        List<SpeakerProposal> speakerProposals = new ArrayList<>();
        String sql = "SELECT speaker_proposals.*, " +
                entityProcessor.getEntityFieldsWithPrefixes(User.class, "u.", "user_") + " " +
                "FROM speaker_proposals LEFT JOIN users u ON speaker_proposals.speaker_id=u.id WHERE speaker_proposals.report_topic_id=?";
        LOGGER.info("Searching for speaker proposals by topic id. Sql: {}", sql);
        try (Connection connection = DbManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, topicId);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                LOGGER.info("Parsing SpeakerProposal");
                SpeakerProposal speakerProposal = entityParser.parseToEntity(SpeakerProposal.class, result);
                LOGGER.info("Parsing User");
                User speaker = entityParser.parseToEntity(User.class, result, "user_");
                speakerProposal.setSpeaker(speaker);

                speakerProposals.add(speakerProposal);
            }
        } catch (SQLException exception) {
            LOGGER.error("Unable to find");
        }
        return speakerProposals;
    }

    @Override
    public List<Integer> findAllSpeakerProposedTopicIdsForMeeting(int meetingId, int speakerId) {
        String sql =
                "SELECT sp.report_topic_id " +
                "FROM speaker_proposals sp " +
                "WHERE speaker_id=? AND EXISTS (" +
                    "SELECT NULL FROM report_topics rt WHERE rt.meeting_id=? AND rt.id=sp.report_topic_id" +
                ")";
        LOGGER.info("Searching for speaker proposed topic ids for meeting. Sql: {}", sql);
        List<Integer> ids = new ArrayList<>();
        try (Connection connection = DbManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, speakerId);
            statement.setInt(2, meetingId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                LOGGER.info("Parsing id");
                ids.add(resultSet.getInt("report_topic_id"));
            }
        } catch (SQLException exception) {
            LOGGER.error("Unable to find", exception);
        }
        return ids;
    }
}
