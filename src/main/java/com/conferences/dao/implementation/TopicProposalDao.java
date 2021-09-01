package com.conferences.dao.implementation;

import com.conferences.config.DbManager;
import com.conferences.dao.abstraction.AbstractDao;
import com.conferences.dao.abstraction.ITopicProposalDao;
import com.conferences.entity.TopicProposal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TopicProposalDao extends AbstractDao<Integer, TopicProposal> implements ITopicProposalDao {

    @Override
    public List<TopicProposal> findAllByMeetingIdWithSpeakers(int meetingId) {
        return new ArrayList<>();
    }

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

            insertStatement = connection.prepareStatement(insertSql);
            insertStatement.setInt(1, topicProposalId);
            insertStatement.executeUpdate();

            deleteStatement = connection.prepareStatement(deleteSql);
            deleteStatement.setInt(1, topicProposalId);
            deleteStatement.executeUpdate();

            return true;
        } catch (SQLException exception) {
            transactionHandler.rollbackTransaction(connection);

            exception.printStackTrace();
        } finally {
            transactionHandler.setAutoCommit(connection, true);

            transactionHandler.closeResource(deleteStatement);
            transactionHandler.closeResource(insertStatement);
            transactionHandler.closeResource(connection);
        }
        return false;
    }
}
