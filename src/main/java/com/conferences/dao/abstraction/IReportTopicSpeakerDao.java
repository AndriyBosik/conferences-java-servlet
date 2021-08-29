package com.conferences.dao.abstraction;

import com.conferences.entity.ReportTopicSpeaker;

public interface IReportTopicSpeakerDao {

    boolean saveWithDeletionFromSpeakerProposalsTable(ReportTopicSpeaker reportTopicSpeaker);

}
