package com.conferences.dao.abstraction;

import com.conferences.entity.SpeakerProposal;

import java.util.List;

public interface ISpeakerProposalDao extends ICrudDao<Integer, SpeakerProposal> {

    List<SpeakerProposal> findAllByTopicIdWithSpeaker(int topicId);

    List<Integer> findAllSpeakerProposedTopicIdsForMeeting(int meetingId, int speakerId);

}
