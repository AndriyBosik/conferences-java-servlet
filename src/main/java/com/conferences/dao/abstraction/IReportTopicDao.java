package com.conferences.dao.abstraction;

import com.conferences.entity.ReportTopic;

public interface IReportTopicDao extends IDao<Integer, ReportTopic> {

    boolean updateWithSpeaker(ReportTopic reportTopic);

    boolean saveWithSpeaker(ReportTopic reportTopic);

}
