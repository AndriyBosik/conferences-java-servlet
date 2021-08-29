package com.conferences.service.abstraction;

import com.conferences.entity.SpeakerProposal;

import java.util.List;

public interface ISpeakerProposalService {

    List<SpeakerProposal> getTopicProposals(int topicId);

}
