package com.conferences.service.abstraction;

import com.conferences.entity.ReportTopic;
import com.conferences.entity.ReportTopicSpeaker;
import com.conferences.model.FormError;

import java.util.List;

public interface IReportTopicService {

    List<FormError> updateTopicWithSpeaker(ReportTopic reportTopic);

    boolean setSpeakerForTopic(ReportTopicSpeaker reportTopicSpeaker);

    List<FormError> saveWithSpeaker(ReportTopic reportTopic);

}
