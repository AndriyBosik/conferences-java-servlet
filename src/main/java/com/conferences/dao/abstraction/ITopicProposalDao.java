package com.conferences.dao.abstraction;

import com.conferences.entity.TopicProposal;

import java.util.List;

public interface ITopicProposalDao extends IDao<Integer, TopicProposal> {

    List<TopicProposal> findAllByMeetingIdWithSpeakers(int meetingId);

    boolean createReportTopicWithProposalDeletion(TopicProposal topicProposal);
}
