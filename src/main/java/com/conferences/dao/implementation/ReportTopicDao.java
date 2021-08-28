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

}
