package com.conferences.dao.implementation;

import com.conferences.config.DbManager;
import com.conferences.dao.abstraction.AbstractCrudDao;
import com.conferences.dao.abstraction.IReportTopicSpeakerDao;
import com.conferences.entity.ReportTopicSpeaker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReportTopicSpeakerDao extends AbstractCrudDao<Integer, ReportTopicSpeaker> implements IReportTopicSpeakerDao {

    private static final Logger LOGGER = LogManager.getLogger(ReportTopicSpeakerDao.class);

    @Override
    public boolean saveWithProposalsDeletion(ReportTopicSpeaker reportTopicSpeaker) {
        String speakerProposalsDeleteQuery = "DELETE FROM speaker_proposals WHERE speaker_id=? AND report_topic_id=?";
        String moderatorProposalsDeleteQuery = "DELETE FROM moderator_proposals WHERE speaker_id=? AND report_topic_id=?";

        Connection connection = null;
        PreparedStatement insertStatement = null;
        PreparedStatement speakerProposalsDeleteStatement = null;
        PreparedStatement moderatorProposalsDeleteStatement = null;
        LOGGER.info("Saving speaker for report topic with proposals deletion");
        try {
            connection = DbManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            LOGGER.info("Transaction started");
            LOGGER.info("Inserting record");
            insertStatement = entityProcessor.prepareInsertStatement(connection, reportTopicSpeaker);
            insertStatement.executeUpdate();
            setGeneratedFields(insertStatement, reportTopicSpeaker);

            LOGGER.info("Deleting speaker proposals. Sql: {}", speakerProposalsDeleteQuery);
            speakerProposalsDeleteStatement = connection.prepareStatement(speakerProposalsDeleteQuery);
            speakerProposalsDeleteStatement.setInt(1, reportTopicSpeaker.getSpeakerId());
            speakerProposalsDeleteStatement.setInt(2, reportTopicSpeaker.getReportTopicId());
            speakerProposalsDeleteStatement.executeUpdate();

            LOGGER.info("Deleting moderator proposals. Sql: {}", moderatorProposalsDeleteQuery);
            moderatorProposalsDeleteStatement = connection.prepareStatement(moderatorProposalsDeleteQuery);
            moderatorProposalsDeleteStatement.setInt(1, reportTopicSpeaker.getSpeakerId());
            moderatorProposalsDeleteStatement.setInt(2, reportTopicSpeaker.getReportTopicId());
            moderatorProposalsDeleteStatement.executeUpdate();

            connection.commit();
            LOGGER.info("Transaction committed");
            return true;
        } catch (SQLException exception) {
            LOGGER.error("Unable to execute transaction", exception);
            LOGGER.info("Rolling back transaction");
            transactionHandler.rollbackTransaction(connection);
        } finally {
            transactionHandler.setAutoCommit(connection, true);

            transactionHandler.closeResource(moderatorProposalsDeleteStatement);
            transactionHandler.closeResource(speakerProposalsDeleteStatement);
            transactionHandler.closeResource(insertStatement);
            transactionHandler.closeResource(connection);
        }
        return false;
    }
}
