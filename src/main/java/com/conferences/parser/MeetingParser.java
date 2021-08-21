package com.conferences.parser;

import com.conferences.model.Meeting;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MeetingParser implements IParser<Meeting> {
    @Override
    public Meeting parseToModel(ResultSet result) throws SQLException {
        return parseToModel(result, "");
    }

    @Override
    public Meeting parseToModel(ResultSet result, String prefix) throws SQLException {
        Meeting meeting = new Meeting();
        meeting.setId(result.getInt(prefix + "id"));
        meeting.setTitle(result.getString(prefix + "title"));
        return meeting;
    }
}
