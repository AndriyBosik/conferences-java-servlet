package com.conferences.dao.implementation;

import com.conferences.config.DbManager;
import com.conferences.dao.abstraction.AbstractDao;
import com.conferences.dao.abstraction.IReportTopicSpeakerDao;
import com.conferences.entity.ReportTopicSpeaker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReportTopicSpeakerDao extends AbstractDao<Integer, ReportTopicSpeaker> implements IReportTopicSpeakerDao {

    @Override
    public boolean saveWithDeletionFromSpeakerProposalsTable(ReportTopicSpeaker reportTopicSpeaker) {
        Connection connection = null;
        PreparedStatement insertStatement = null;
        PreparedStatement deleteStatement = null;
        try {
            connection = DbManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            insertStatement = entityProcessor.prepareInsertStatement(connection, reportTopicSpeaker);
            insertStatement.executeUpdate();
            setGeneratedFields(insertStatement, reportTopicSpeaker);

            String deleteSql = "DELETE FROM speaker_proposals WHERE speaker_id=? AND report_topic_id=?";
            deleteStatement = connection.prepareStatement(deleteSql);
            deleteStatement.setInt(1, reportTopicSpeaker.getSpeakerId());
            deleteStatement.setInt(2, reportTopicSpeaker.getReportTopicId());
            deleteStatement.executeUpdate();

            connection.commit();

            return true;
        } catch (SQLException exception) {
            transactionHandler.rollbackTransaction(connection);
            exception.printStackTrace();
        } finally {
            transactionHandler.setAutoCommit(connection, true);

            transactionHandler.closeResource(insertStatement);
            transactionHandler.closeResource(deleteStatement);
            transactionHandler.closeResource(connection);
        }
        return false;
    }
}
