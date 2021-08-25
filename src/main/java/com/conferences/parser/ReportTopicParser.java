package com.conferences.parser;

import com.conferences.entity.ReportTopic;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportTopicParser implements IParser<ReportTopic> {
    @Override
    public ReportTopic parseToModel(ResultSet result) throws SQLException {
        return parseToModel(result, "");
    }

    @Override
    public ReportTopic parseToModel(ResultSet result, String prefix) throws SQLException {
        ReportTopic reportTopic = new ReportTopic();
        reportTopic.setId(result.getInt(prefix + "id"));
        reportTopic.setTitle(result.getString(prefix + "title"));
        reportTopic.setMeetingId(result.getInt(prefix + "meeting_id"));
        return reportTopic;
    }
}
