package com.conferences.dao.implementation;

import com.conferences.dao.abstraction.AbstractDao;
import com.conferences.dao.abstraction.ITopicProposalDao;
import com.conferences.entity.TopicProposal;

import java.util.ArrayList;
import java.util.List;

public class TopicProposalDao extends AbstractDao<Integer, TopicProposal> implements ITopicProposalDao {

    @Override
    public List<TopicProposal> findAllByMeetingIdWithSpeakers(int meetingId) {
        return new ArrayList<>();
    }

    @Override
    public boolean createReportTopicWithProposalDeletion(TopicProposal topicProposal) {
        return false;
    }
}
