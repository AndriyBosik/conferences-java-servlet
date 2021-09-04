package com.conferences.service.abstraction;

import com.conferences.entity.TopicProposal;
import com.conferences.model.FormError;

import java.util.List;

public interface ITopicProposalService {

    List<FormError> addTopicProposal(TopicProposal topicProposal);

    boolean rejectTopicProposal(int topicProposalId);

    boolean acceptTopicProposal(int topicProposalId);

    int getProposedTopicsCount();

}
