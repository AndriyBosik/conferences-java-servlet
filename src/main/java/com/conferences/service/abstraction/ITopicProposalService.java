package com.conferences.service.abstraction;

import com.conferences.entity.TopicProposal;

import java.util.List;

public interface ITopicProposalService {

    boolean addTopicProposal(TopicProposal topicProposal);

    boolean rejectTopicProposal(TopicProposal topicProposal);

    boolean acceptTopicProposal(TopicProposal topicProposal);

    List<TopicProposal> getAllByMeetingWithSpeakers(int meetingId);

}
