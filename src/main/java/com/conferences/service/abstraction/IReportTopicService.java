package com.conferences.service.abstraction;

import com.conferences.entity.ReportTopic;

public interface IReportTopicService {

    boolean save(ReportTopic reportTopic);

    boolean update(ReportTopic reportTopic);

    boolean setSpeakerForTopic(int topicId, int speakerId);

}
