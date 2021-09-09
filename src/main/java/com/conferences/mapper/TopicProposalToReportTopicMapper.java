package com.conferences.mapper;

import com.conferences.entity.ReportTopic;
import com.conferences.entity.TopicProposal;

/**
 * {@inheritDoc}
 */
public class TopicProposalToReportTopicMapper implements IMapper<TopicProposal, ReportTopic> {

    /**
     * <p>
     *     Maps object of {@link TopicProposal} class to object of {@link ReportTopic} class
     * </p>
     */
    @Override
    public ReportTopic map(TopicProposal proposal) {
        ReportTopic reportTopic = new ReportTopic();
        reportTopic.setMeetingId(proposal.getMeetingId());
        reportTopic.setTitle(proposal.getTopicTitle());
        return reportTopic;
    }
}
