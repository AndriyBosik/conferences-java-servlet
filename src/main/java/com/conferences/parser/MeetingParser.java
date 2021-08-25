package com.conferences.parser;

import com.conferences.entity.Meeting;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MeetingParser implements IParser<Meeting> {
    @Override
    public Meeting parseToModel(ResultSet result) throws SQLException {
        return parseToModel(result, "");
    }

    @Override
    public Meeting parseToModel(ResultSet result, String prefix) throws SQLException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Meeting meeting = new Meeting();

        meeting.setId(result.getInt(prefix + "id"));
        meeting.setTitle(result.getString(prefix + "title"));
        meeting.setDescription(result.getString(prefix + "description"));
        meeting.setImagePath(result.getString(prefix + "image_path"));

        Timestamp ts = result.getTimestamp(prefix + "date");
        Date date = new Date(ts.getTime());
        meeting.setDate(date);

        return meeting;
    }
}
