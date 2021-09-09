package com.conferences.service.abstraction;

/**
 * <p>
 *     Defines methods to interact with speaker
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public interface ISpeakerService {

    /**
     * <p>
     *     Proposed speaker for specific topic
     * </p>
     * @param reportTopicId id of topic to propose speaker for
     * @param userId id of speaker
     * @return true if speaker was proposed to topic, false otherwise
     */
    boolean proposeSpeaker(int reportTopicId, int userId);
}
