package com.conferences.dao.implementation;

import com.conferences.config.DbManager;
import com.conferences.dao.abstraction.AbstractCrudDao;
import com.conferences.dao.abstraction.IReportTopicDao;
import com.conferences.entity.ReportTopic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * {@inheritDoc}
 */
public class ReportTopicDao extends AbstractCrudDao<Integer, ReportTopic> implements IReportTopicDao {

    private static final Logger LOGGER = LogManager.getLogger(ReportTopicDao.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateWithSpeaker(ReportTopic reportTopic) {
        Connection connection = null;
        PreparedStatement reportTopicSpeakerStatement = null;
        PreparedStatement reportTopicStatement = null;
        LOGGER.info("Updating ReportTopic with user");
        try {
            connection = DbManager.getInstance().getConnection();
            connection.setAutoCommit(false);

            LOGGER.info("Transaction started");
            if (reportTopic.getReportTopicSpeaker() == null) {
                String deleteSql = "DELETE FROM report_topics_speakers WHERE report_topic_id=?";
                reportTopicSpeakerStatement = connection.prepareStatement(deleteSql);
                reportTopicSpeakerStatement.setInt(1, reportTopic.getId());
                LOGGER.info("Deleting from report_topics_speakers. Sql: {}", deleteSql);
            } else if (reportTopic.getReportTopicSpeaker().getId() != null){
                LOGGER.info("Updating ReportTopicSpeaker");
                reportTopicSpeakerStatement = entityProcessor.prepareUpdateStatement(connection, reportTopic.getReportTopicSpeaker());
            } else {
                LOGGER.info("Inserting ReportTopicSpeaker");
                reportTopicSpeakerStatement = entityProcessor.prepareInsertStatement(connection, reportTopic.getReportTopicSpeaker());
            }
            reportTopicSpeakerStatement.executeUpdate();

            reportTopicStatement = entityProcessor.prepareUpdateStatement(connection, reportTopic);
            reportTopicStatement.executeUpdate();

            connection.commit();
            LOGGER.info("Transaction committed");
            return true;
        } catch (SQLException exception) {
            LOGGER.error("Unable to process transaction", exception);
            LOGGER.info("Rolling back transaction");
            transactionHandler.rollbackTransaction(connection);
        } finally {
            transactionHandler.setAutoCommit(connection, true);

            transactionHandler.closeResource(reportTopicSpeakerStatement);
            transactionHandler.closeResource(reportTopicStatement);
            transactionHandler.closeResource(connection);
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean saveWithSpeaker(ReportTopic reportTopic) {
        Connection connection = null;
        PreparedStatement insertReportTopicStatement = null;
        PreparedStatement insertReportTopicSpeakerStatement = null;
        LOGGER.info("Saving ReportTopic with speaker");
        try {
            connection = DbManager.getInstance().getConnection();
            connection.setAutoCommit(false);

            LOGGER.info("Transaction started");
            insertReportTopicStatement = entityProcessor.prepareInsertStatement(connection, reportTopic);
            LOGGER.info("Inserting ReportTopic");
            insertReportTopicStatement.executeUpdate();
            setGeneratedFields(insertReportTopicStatement, reportTopic);

            if (reportTopic.getReportTopicSpeaker() != null) {
                reportTopic.getReportTopicSpeaker().setReportTopicId(reportTopic.getId());
                insertReportTopicSpeakerStatement = entityProcessor.prepareInsertStatement(connection, reportTopic.getReportTopicSpeaker());
                LOGGER.info("Inserting ReportTopicSpeaker");
                insertReportTopicSpeakerStatement.executeUpdate();
                LOGGER.info("Setting generated fields");
                setGeneratedFields(insertReportTopicSpeakerStatement, reportTopic.getReportTopicSpeaker());
            }

            connection.commit();
            LOGGER.info("Transaction committed");
            return true;
        } catch (SQLException exception) {
            LOGGER.error("Unable to process transaction", exception);
            LOGGER.info("Rolling back transaction");
            transactionHandler.rollbackTransaction(connection);
        } finally {
            transactionHandler.setAutoCommit(connection, true);

            transactionHandler.closeResource(connection);
            transactionHandler.closeResource(insertReportTopicStatement);
            transactionHandler.closeResource(insertReportTopicSpeakerStatement);
        }
        return false;
    }
}
