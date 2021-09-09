package com.conferences.service.abstraction;

import com.conferences.entity.custom.ProposedTopicData;

import java.util.List;

/**
 * <p>
 *     Defines function to get information about proposed topics
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public interface IProposedTopicDataService {

    /**
     * <p>
     *     Gets proposed topics for all meetings from all speakers
     * </p>
     * @return list of {@link ProposedTopicData}
     */
    List<ProposedTopicData> getProposedTopicsOrderByMeeting();
}
