package com.conferences.dao.implementation;

import com.conferences.config.DbManager;
import com.conferences.dao.abstraction.AbstractDao;
import com.conferences.dao.abstraction.IModeratorProposalDao;
import com.conferences.entity.ModeratorProposal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ModeratorProposalDao extends AbstractDao<Integer, ModeratorProposal> implements IModeratorProposalDao {

    @Override
    public boolean deleteProposal(ModeratorProposal moderatorProposal) {
        String sql = "DELETE FROM moderator_proposals WHERE speaker_id=? AND report_topic_id=?";
        try (Connection connection = DbManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, moderatorProposal.getSpeakerId());
            statement.setInt(2, moderatorProposal.getReportTopicId());
            statement.executeUpdate();

            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }
}
