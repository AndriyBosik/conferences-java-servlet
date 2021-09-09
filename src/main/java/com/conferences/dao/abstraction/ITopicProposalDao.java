package com.conferences.dao.abstraction;

import com.conferences.entity.TopicProposal;
import com.conferences.entity.ReportTopic;

/**
 * <p>
 *     Defines methods to process topic_proposals table
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public interface ITopicProposalDao extends ICrudDao<Integer, TopicProposal> {

    /**
     * <p>
     *     Creates {@link ReportTopic} based on {@link TopicProposal}
     * </p>
     * @param topicProposalId id of {@link TopicProposal}
     * @return true if {@link ReportTopic} was successfully created, false otherwise
     */
    boolean createReportTopicWithProposalDeletion(int topicProposalId);
}
