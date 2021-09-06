package com.conferences.dao.implementation;

import com.conferences.config.DbManager;
import com.conferences.dao.abstraction.AbstractCrudDao;
import com.conferences.dao.abstraction.ITopicProposalDao;
import com.conferences.entity.TopicProposal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TopicProposalDao extends AbstractCrudDao<Integer, TopicProposal> implements ITopicProposalDao {

    private static final Logger LOGGER = LogManager.getLogger(TopicProposalDao.class);

    @Override
    public boolean createReportTopicWithProposalDeletion(int topicProposalId) {
        Connection connection = null;

        String insertSql =
            "INSERT INTO report_topics(title, meeting_id) " +
            "SELECT tp.topic_title, tp.meeting_id FROM topic_proposals tp WHERE id=?";
        PreparedStatement insertStatement = null;

        String deleteSql =
            "DELETE FROM topic_proposals WHERE id=?";
        PreparedStatement deleteStatement = null;
        try {
            connection = DbManager.getInstance().getConnection();
            transactionHandler.setAutoCommit(connection, false);

            LOGGER.info("Transaction started");
            LOGGER.info("Inserting ReportTopic. Sql: {}", insertSql);
            insertStatement = connection.prepareStatement(insertSql);
            insertStatement.setInt(1, topicProposalId);
            insertStatement.executeUpdate();

            LOGGER.info("Deleting ReportTopic. Sql: {}", deleteSql);
            deleteStatement = connection.prepareStatement(deleteSql);
            deleteStatement.setInt(1, topicProposalId);
            deleteStatement.executeUpdate();

            connection.commit();
            LOGGER.info("Transaction committed");
            return true;
        } catch (SQLException exception) {
            LOGGER.error("Unable to process transaction", exception);
            LOGGER.info("Rolling back transaction");
            transactionHandler.rollbackTransaction(connection);
        } finally {
            transactionHandler.setAutoCommit(connection, true);

            LOGGER.info("Closing resources");
            transactionHandler.closeResource(deleteStatement);
            transactionHandler.closeResource(insertStatement);
            transactionHandler.closeResource(connection);
        }
        return false;
    }
}
