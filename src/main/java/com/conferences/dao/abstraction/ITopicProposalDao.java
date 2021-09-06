package com.conferences.dao.abstraction;

import com.conferences.entity.TopicProposal;

import java.util.List;

public interface ITopicProposalDao extends ICrudDao<Integer, TopicProposal> {

    boolean createReportTopicWithProposalDeletion(int topicProposalId);
}
