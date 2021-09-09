package com.conferences.dao.abstraction;

import com.conferences.entity.SpeakerProposal;

import java.util.List;

import com.conferences.entity.ReportTopic;
import com.conferences.entity.Meeting;
import com.conferences.entity.User;

/**
 * <p>
 *     Defines methods to process speaker_proposals table
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public interface ISpeakerProposalDao extends ICrudDao<Integer, SpeakerProposal> {

    /**
     * <p>
     *      Retrieves all speakers' proposals for concrete {@link ReportTopic}
     * </p>
     * @param topicId id of {@link ReportTopic}
     * @return list of {@link SpeakerProposal}
     */
    List<SpeakerProposal> findAllByTopicIdWithSpeaker(int topicId);

    /**
     * <p>
     *     Returns list of topic ids for specific meeting for which speaker propose himself
     * </p>
     * @param meetingId id of {@link Meeting}
     * @param speakerId id of speaker({@link User})
     * @return list of {@link ReportTopic} ids for which speaker proposed himself
     */
    List<Integer> findAllSpeakerProposedTopicIdsForMeeting(int meetingId, int speakerId);
}
