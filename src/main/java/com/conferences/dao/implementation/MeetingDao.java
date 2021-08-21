package com.conferences.dao.implementation;

import com.conferences.dao.abstraction.AbstractDao;
import com.conferences.dao.abstraction.IMeetingDao;
import com.conferences.model.Meeting;
import com.conferences.parser.IParser;
import com.conferences.parser.MeetingParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MeetingDao extends AbstractDao<Integer, Meeting> implements IMeetingDao {
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String DATE = "date";

    private IParser<Meeting> meetingParser;

    public MeetingDao() {
        meetingParser = new MeetingParser();
    }

    @Override
    protected String getTableName() {
        return "meetings";
    }

    @Override
    protected String getKeyName() {
        return ID;
    }

    @Override
    protected IParser<Meeting> getParser() {
        return meetingParser;
    }

    @Override
    protected PreparedStatement getInsertStatement(Connection connection, Meeting model) throws SQLException {
        return null;
    }

    @Override
    protected PreparedStatement getUpdateStatement(Connection connection, Integer key, Meeting model) throws SQLException {
        return null;
    }
}
