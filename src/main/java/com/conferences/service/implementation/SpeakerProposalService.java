package com.conferences.service.implementation;

import com.conferences.dao.abstraction.ISpeakerProposalDao;
import com.conferences.dao.implementation.SpeakerProposalDao;
import com.conferences.entity.SpeakerProposal;
import com.conferences.service.abstraction.ISpeakerProposalService;

import java.util.List;

public class SpeakerProposalService implements ISpeakerProposalService {

    private final ISpeakerProposalDao speakerProposalDao;

    public SpeakerProposalService() {
        this.speakerProposalDao = new SpeakerProposalDao();
    }

    @Override
    public List<SpeakerProposal> getTopicProposals(int topicId) {
        return speakerProposalDao.findAllByTopicIdWithSpeaker(topicId);
    }

    @Override
    public List<Integer> getSpeakerProposedTopicIdsForMeeting(int meetingId, int speakerId) {
        return speakerProposalDao.findAllSpeakerProposedTopicIdsForMeeting(meetingId, speakerId);
    }
}
