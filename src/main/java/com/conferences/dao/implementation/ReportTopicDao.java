package com.conferences.dao.implementation;

import com.conferences.config.DbManager;
import com.conferences.dao.abstraction.AbstractDao;
import com.conferences.dao.abstraction.IReportTopicDao;
import com.conferences.entity.ReportTopic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReportTopicDao extends AbstractDao<Integer, ReportTopic> implements IReportTopicDao {

    @Override
    public boolean updateSpeakerIdForTopic(int topicId, int speakerId) {
        String updateSql = "UPDATE report_topics SET speaker_id=? WHERE id=?";
        String deleteSql = "DELETE FROM speaker_proposals WHERE speaker_id=? AND report_topic_id=?";

        Connection connection = null;
        try {
            connection = DbManager.getInstance().getConnection();
            connection.setAutoCommit(false);

            PreparedStatement updateStatement = connection.prepareStatement(updateSql);
            updateStatement.setInt(1, speakerId);
            updateStatement.setInt(2, topicId);
            updateStatement.executeUpdate();

            PreparedStatement deleteStatement = connection.prepareStatement(deleteSql);
            deleteStatement.setInt(1, speakerId);
            deleteStatement.setInt(2, topicId);
            deleteStatement.executeUpdate();

            connection.commit();

            return true;
        } catch (SQLException exception) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            exception.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        }
        return false;
    }
}
