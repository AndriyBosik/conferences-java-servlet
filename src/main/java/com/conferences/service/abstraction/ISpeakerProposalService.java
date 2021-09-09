package com.conferences.service.abstraction;

import com.conferences.entity.SpeakerProposal;
import com.conferences.entity.ReportTopic;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public interface ISpeakerProposalService {

    /**
     * <p>
     *      Returns speakers which proposed themselves for specified topic
     * </p>
     * @param topicId id of topic to get speakers proposals for
     * @return list of {@link SpeakerProposal}
     */
    List<SpeakerProposal> getTopicProposals(int topicId);

    /**
     * <p>
     *      Returns list of topic ids for specific meeting for which speaker propose himself
     * </p>
     * @param meetingId id of meeting to get topic ids for
     * @param speakerId id of speaker to get proposed topic ids for
     * @return list of {@link ReportTopic} ids for which speaker proposed himself
     */
    List<Integer> getSpeakerProposedTopicIdsForMeeting(int meetingId, int speakerId);
}
