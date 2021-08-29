package com.conferences.service.abstraction;

import com.conferences.entity.ReportTopic;
import com.conferences.entity.ReportTopicSpeaker;

public interface IReportTopicService {

    boolean save(ReportTopic reportTopic);

    boolean updateTopicWithSpeaker(ReportTopic reportTopic);

    boolean setSpeakerForTopic(ReportTopicSpeaker reportTopicSpeaker);

    boolean saveWithSpeaker(ReportTopic reportTopic);

}
