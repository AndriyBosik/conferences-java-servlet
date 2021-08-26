package com.conferences.dao.implementation;

import com.conferences.dao.abstraction.AbstractDao;
import com.conferences.dao.abstraction.IReportTopicDao;
import com.conferences.entity.ReportTopic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReportTopicDao extends AbstractDao<Integer, ReportTopic> implements IReportTopicDao {
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String MEETING_ID = "meeting_id";

    @Override
    protected PreparedStatement getInsertStatement(Connection connection, ReportTopic model) throws SQLException {
        String sql = "INSERT INTO " + dbTable.getName() + "(" + TITLE + ", " + MEETING_ID + ") VALUES(?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, model.getTitle());
        statement.setInt(2, model.getMeetingId());
        return statement;
    }

    @Override
    protected PreparedStatement getUpdateStatement(Connection connection, Integer key, ReportTopic model) throws SQLException {
        String sql = "UPDATE " + dbTable.getName() + " SET " + TITLE + "=?, " + MEETING_ID + "=?" + " WHERE " + dbTable.getKey() + "=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, model.getTitle());
        statement.setInt(2, model.getMeetingId());
        statement.setInt(3, key);
        return statement;
    }
}
