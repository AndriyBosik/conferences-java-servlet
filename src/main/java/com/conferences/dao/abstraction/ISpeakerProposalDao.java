package com.conferences.dao.abstraction;

import com.conferences.entity.SpeakerProposal;

import java.util.List;

public interface ISpeakerProposalDao extends IDao<Integer, SpeakerProposal> {

    List<SpeakerProposal> findAllByTopicIdWithSpeaker(int topicId);
}
