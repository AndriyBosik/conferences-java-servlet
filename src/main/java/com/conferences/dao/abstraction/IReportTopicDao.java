package com.conferences.dao.abstraction;

import com.conferences.entity.ReportTopic;

public interface IReportTopicDao extends IDao<Integer, ReportTopic> {

    boolean updateSpeakerIdForTopic(int topicId, int speakerId);

}
