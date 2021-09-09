package com.conferences.service.abstraction;

import com.conferences.entity.TopicProposal;
import com.conferences.model.FormError;

import java.util.List;

/**
 * <p>
 *     Defines methods to process {@link TopicProposal} data
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public interface ITopicProposalService {

    /**
     * <p>
     *     Adds topic proposal
     * </p>
     * @param topicProposal proposal to add
     * @return list of errors which may occur while topic addition
     */
    List<FormError> addTopicProposal(TopicProposal topicProposal);

    /**
     * <p>
     *     Rejects topic proposal
     * </p>
     * @param topicProposalId id of topic proposal to reject
     * @return true if proposal was successfully rejected, false otherwise
     */
    boolean rejectTopicProposal(int topicProposalId);

    /**
     * <p>
     *     Accepts topic proposal
     * </p>
     * @param topicProposalId id of topic proposal to accept
     * @return true if proposal was successfully accepted, false otherwise
     */
    boolean acceptTopicProposal(int topicProposalId);

    /**
     * <p>
     *     Returns count of proposed topics
     * </p>
     * @return number representing count of proposed topics
     */
    int getProposedTopicsCount();
}
