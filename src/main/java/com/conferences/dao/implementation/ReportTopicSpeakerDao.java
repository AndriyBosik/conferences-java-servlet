package com.conferences.dao.implementation;

import com.conferences.config.DbManager;
import com.conferences.dao.abstraction.AbstractCrudDao;
import com.conferences.dao.abstraction.IReportTopicSpeakerDao;
import com.conferences.entity.ReportTopicSpeaker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReportTopicSpeakerDao extends AbstractCrudDao<Integer, ReportTopicSpeaker> implements IReportTopicSpeakerDao {

    @Override
    public boolean saveWithProposalsDeletionTable(ReportTopicSpeaker reportTopicSpeaker) {
        Connection connection = null;
        PreparedStatement insertStatement = null;
        PreparedStatement speakerProposalsDeleteStatement = null;
        PreparedStatement moderatorProposalsDeleteStatement = null;
        try {
            connection = DbManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            insertStatement = entityProcessor.prepareInsertStatement(connection, reportTopicSpeaker);
            insertStatement.executeUpdate();
            setGeneratedFields(insertStatement, reportTopicSpeaker);

            String speakerProposalsDeleteQuery = "DELETE FROM speaker_proposals WHERE speaker_id=? AND report_topic_id=?";
            speakerProposalsDeleteStatement = connection.prepareStatement(speakerProposalsDeleteQuery);
            speakerProposalsDeleteStatement.setInt(1, reportTopicSpeaker.getSpeakerId());
            speakerProposalsDeleteStatement.setInt(2, reportTopicSpeaker.getReportTopicId());
            speakerProposalsDeleteStatement.executeUpdate();

            String moderatorProposalsDeleteQuery = "DELETE FROM moderator_proposals WHERE speaker_id=? AND report_topic_id=?";
            moderatorProposalsDeleteStatement = connection.prepareStatement(moderatorProposalsDeleteQuery);
            moderatorProposalsDeleteStatement.setInt(1, reportTopicSpeaker.getSpeakerId());
            moderatorProposalsDeleteStatement.setInt(2, reportTopicSpeaker.getReportTopicId());
            moderatorProposalsDeleteStatement.executeUpdate();

            connection.commit();

            return true;
        } catch (SQLException exception) {
            transactionHandler.rollbackTransaction(connection);
            exception.printStackTrace();
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
