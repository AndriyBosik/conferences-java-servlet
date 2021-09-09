package com.conferences.service.implementation;

import com.conferences.dao.abstraction.ISpeakerProposalDao;
import com.conferences.dao.implementation.SpeakerProposalDao;
import com.conferences.entity.SpeakerProposal;
import com.conferences.entity.custom.ProposalData;
import com.conferences.factory.DaoFactory;
import com.conferences.service.abstraction.ISpeakerProposalService;

import java.util.List;

/**
 * {@inheritDoc}
 */
public class SpeakerProposalService implements ISpeakerProposalService {

    private final ISpeakerProposalDao speakerProposalDao;

    public SpeakerProposalService() {
        this.speakerProposalDao = DaoFactory.getInstance().getSpeakerProposalDao();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SpeakerProposal> getTopicProposals(int topicId) {
        return speakerProposalDao.findAllByTopicIdWithSpeaker(topicId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Integer> getSpeakerProposedTopicIdsForMeeting(int meetingId, int speakerId) {
        return speakerProposalDao.findAllSpeakerProposedTopicIdsForMeeting(meetingId, speakerId);
    }
}
