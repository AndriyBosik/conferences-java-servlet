package com.conferences.dao.implementation;

import com.conferences.config.DbManager;
import com.conferences.dao.abstraction.AbstractCrudDao;
import com.conferences.dao.abstraction.IReportTopicDao;
import com.conferences.entity.ReportTopic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReportTopicDao extends AbstractCrudDao<Integer, ReportTopic> implements IReportTopicDao {

    @Override
    public boolean updateWithSpeaker(ReportTopic reportTopic) {
        Connection connection = null;
        PreparedStatement reportTopicSpeakerStatement = null;
        PreparedStatement reportTopicStatement = null;
        try {
            connection = DbManager.getInstance().getConnection();
            connection.setAutoCommit(false);

            if (reportTopic.getReportTopicSpeaker() == null) {
                String deleteSql = "DELETE FROM report_topics_speakers WHERE report_topic_id=?";
                reportTopicSpeakerStatement = connection.prepareStatement(deleteSql);
                reportTopicSpeakerStatement.setInt(1, reportTopic.getId());
            } else if (reportTopic.getReportTopicSpeaker().getId() != null){
                reportTopicSpeakerStatement = entityProcessor.prepareUpdateStatement(connection, reportTopic.getReportTopicSpeaker());
            } else {
                reportTopicSpeakerStatement = entityProcessor.prepareInsertStatement(connection, reportTopic.getReportTopicSpeaker());
            }
            reportTopicSpeakerStatement.executeUpdate();

            reportTopicStatement = entityProcessor.prepareUpdateStatement(connection, reportTopic);
            reportTopicStatement.executeUpdate();

            connection.commit();
            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
            transactionHandler.rollbackTransaction(connection);
        } finally {
            transactionHandler.setAutoCommit(connection, true);

            transactionHandler.closeResource(reportTopicSpeakerStatement);
            transactionHandler.closeResource(reportTopicStatement);
            transactionHandler.closeResource(connection);
        }
        return false;
    }

    @Override
    public boolean saveWithSpeaker(ReportTopic reportTopic) {
        Connection connection = null;
        PreparedStatement insertReportTopicStatement = null;
        PreparedStatement insertReportTopicSpeakerStatement = null;
        try {
            connection = DbManager.getInstance().getConnection();
            connection.setAutoCommit(false);

            insertReportTopicStatement = entityProcessor.prepareInsertStatement(connection, reportTopic);
            insertReportTopicStatement.executeUpdate();
            setGeneratedFields(insertReportTopicStatement, reportTopic);

            if (reportTopic.getReportTopicSpeaker() != null) {
                reportTopic.getReportTopicSpeaker().setReportTopicId(reportTopic.getId());
                insertReportTopicSpeakerStatement = entityProcessor.prepareInsertStatement(connection, reportTopic.getReportTopicSpeaker());
                insertReportTopicSpeakerStatement.executeUpdate();
                setGeneratedFields(insertReportTopicSpeakerStatement, reportTopic.getReportTopicSpeaker());
            }

            connection.commit();
            return true;
        } catch (SQLException exception) {
            transactionHandler.rollbackTransaction(connection);
            exception.printStackTrace();
        } finally {
            transactionHandler.setAutoCommit(connection, true);

            transactionHandler.closeResource(connection);
            transactionHandler.closeResource(insertReportTopicStatement);
            transactionHandler.closeResource(insertReportTopicSpeakerStatement);
        }
        return false;
    }
}
