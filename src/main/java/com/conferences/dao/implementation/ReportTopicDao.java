package com.conferences.dao.implementation;

import com.conferences.dao.abstraction.AbstractDao;
import com.conferences.dao.abstraction.IReportTopicDao;
import com.conferences.entity.ReportTopic;
import com.conferences.parser.IParser;
import com.conferences.parser.ReportTopicParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReportTopicDao extends AbstractDao<Integer, ReportTopic> implements IReportTopicDao {
    private static final String TABLE_NAME = "report_topics";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String MEETING_ID = "meeting_id";

    private IParser<ReportTopic> reportTopicParser;

    public ReportTopicDao() {
        reportTopicParser = new ReportTopicParser();
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected String getKeyName() {
        return ID;
    }

    @Override
    protected IParser<ReportTopic> getParser() {
        return reportTopicParser;
    }

    @Override
    protected PreparedStatement getInsertStatement(Connection connection, ReportTopic model) throws SQLException {
        String sql = "INSERT INTO " + TABLE_NAME + "(" + TITLE + ", " + MEETING_ID + ") VALUES(?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, model.getTitle());
        statement.setInt(2, model.getMeetingId());
        return statement;
    }

    @Override
    protected PreparedStatement getUpdateStatement(Connection connection, Integer key, ReportTopic model) throws SQLException {
        String sql = "UPDATE " + TABLE_NAME + " SET " + TITLE + "=?, " + MEETING_ID + "=?" + " WHERE " + getKeyName() + "=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, model.getTitle());
        statement.setInt(2, model.getMeetingId());
        statement.setInt(3, key);
        return statement;
    }
}
