package com.conferences.mapper;

import com.conferences.entity.ReportTopic;
import com.conferences.entity.TopicProposal;

public class TopicProposalToReportTopicMapper implements IMapper<TopicProposal, ReportTopic> {

    @Override
    public ReportTopic map(TopicProposal proposal) {
        ReportTopic reportTopic = new ReportTopic();
        reportTopic.setMeetingId(proposal.getMeetingId());
        reportTopic.setTitle(proposal.getTopicTitle());
        return reportTopic;
    }
}
