package com.conferences.dao.abstraction;

import com.conferences.entity.custom.ProposedTopicData;

import java.util.List;

/**
 * <p>
 *     Defines method to process proposed topics
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public interface IProposedTopicDataDao extends IDao<Integer, ProposedTopicData> {

    /**
     * <p>
     *     Finds all proposed topics for all meetings sorting by meetingId
     * </p>
     * @return list of {@link ProposedTopicData} objects
     */
    List<ProposedTopicData> findAllProposedTopicsOrderByMeetingId();
}
