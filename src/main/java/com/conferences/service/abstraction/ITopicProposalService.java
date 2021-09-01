package com.conferences.service.abstraction;

import com.conferences.entity.TopicProposal;

import java.util.List;

public interface ITopicProposalService {

    boolean addTopicProposal(TopicProposal topicProposal);

    boolean rejectTopicProposal(int topicProposalId);

    boolean acceptTopicProposal(int topicProposalId);

    List<TopicProposal> getAllByMeetingWithSpeakers(int meetingId);

    int getProposedTopicsCount();

}
