package com.conferences.dao.implementation;

import com.conferences.config.DbManager;
import com.conferences.dao.abstraction.AbstractDao;
import com.conferences.dao.abstraction.ISpeakerProposalDao;
import com.conferences.entity.SpeakerProposal;
import com.conferences.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SpeakerProposalDao extends AbstractDao<Integer, SpeakerProposal> implements ISpeakerProposalDao {

    @Override
    public List<SpeakerProposal> findAllByTopicIdWithSpeaker(int topicId) {
        List<SpeakerProposal> speakerProposals = new ArrayList<>();
        String sql = "SELECT speaker_proposals.*, " +
                entityProcessor.getEntityFieldsWithPrefixes(User.class, "u.", "user_") + " " +
                "FROM speaker_proposals LEFT JOIN users u ON speaker_proposals.speaker_id=u.id WHERE speaker_proposals.report_topic_id=?";
        try (Connection connection = DbManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, topicId);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                SpeakerProposal speakerProposal = entityParser.parseToEntity(SpeakerProposal.class, result);
                User speaker = entityParser.parseToEntity(User.class, result, "user_");
                speakerProposal.setSpeaker(speaker);

                speakerProposals.add(speakerProposal);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return speakerProposals;
    }
}
