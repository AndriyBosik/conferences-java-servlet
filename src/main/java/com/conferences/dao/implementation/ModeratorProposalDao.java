package com.conferences.dao.implementation;

import com.conferences.config.DbManager;
import com.conferences.dao.abstraction.AbstractCrudDao;
import com.conferences.dao.abstraction.IModeratorProposalDao;
import com.conferences.entity.ModeratorProposal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ModeratorProposalDao extends AbstractCrudDao<Integer, ModeratorProposal> implements IModeratorProposalDao {

    private static final Logger LOGGER = LogManager.getLogger(ModeratorProposalDao.class);

    @Override
    public boolean deleteProposal(ModeratorProposal moderatorProposal) {
        String sql = "DELETE FROM moderator_proposals WHERE speaker_id=? AND report_topic_id=?";
        LOGGER.info("Deleting moderator proposal: {}", sql);
        try (Connection connection = DbManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, moderatorProposal.getSpeakerId());
            statement.setInt(2, moderatorProposal.getReportTopicId());
            statement.executeUpdate();

            return true;
        } catch (SQLException exception) {
            LOGGER.error("Unable to delete", exception);
        }
        return false;
    }
}
