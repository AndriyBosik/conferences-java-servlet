package com.conferences.service.abstraction;

import com.conferences.entity.SpeakerProposal;
import com.conferences.entity.custom.ProposalData;

import java.util.List;

public interface ISpeakerProposalService {

    List<SpeakerProposal> getTopicProposals(int topicId);

    List<Integer> getSpeakerProposedTopicIdsForMeeting(int meetingId, int speakerId);

}
