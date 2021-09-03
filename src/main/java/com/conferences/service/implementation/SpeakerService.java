package com.conferences.service.implementation;

import com.conferences.dao.abstraction.ISpeakerProposalDao;
import com.conferences.dao.implementation.SpeakerProposalDao;
import com.conferences.entity.SpeakerProposal;
import com.conferences.factory.DaoFactory;
import com.conferences.service.abstraction.ISpeakerService;

public class SpeakerService implements ISpeakerService {

    private final ISpeakerProposalDao speakerProposalDao;

    public SpeakerService() {
        speakerProposalDao = DaoFactory.getInstance().getSpeakerProposalDao();
    }

    @Override
    public boolean proposeSpeaker(int reportTopicId, int userId) {
        SpeakerProposal speakerProposal = new SpeakerProposal();
        speakerProposal.setReportTopicId(reportTopicId);
        speakerProposal.setSpeakerId(userId);
        return speakerProposalDao.create(speakerProposal);
    }
}
