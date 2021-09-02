package com.conferences.dao.abstraction;

import com.conferences.entity.ReportTopicSpeaker;

public interface IReportTopicSpeakerDao extends ICrudDao<Integer, ReportTopicSpeaker> {

    boolean saveWithProposalsDeletionTable(ReportTopicSpeaker reportTopicSpeaker);

}
