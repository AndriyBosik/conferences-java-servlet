package com.conferences.service.implementation;

import com.conferences.dao.abstraction.IProposalDataDao;
import com.conferences.dao.implementation.ProposalDataDao;
import com.conferences.entity.custom.ProposalData;
import com.conferences.factory.DaoFactory;
import com.conferences.service.abstraction.IProposalDataService;

import java.util.List;

public class ProposalDataService implements IProposalDataService {

    private final IProposalDataDao proposalDataDao;

    public ProposalDataService() {
        proposalDataDao = DaoFactory.getInstance().getProposalDataDao();
    }

    @Override
    public List<ProposalData> getActiveModeratorProposalsForSpeakerOrderByMeeting(int speakerId) {
        return proposalDataDao.findActiveModeratorProposalsForSpeakerOrderByMeeting(speakerId);
    }

    @Override
    public List<ProposalData> getActiveSpeakerProposalsOrderByMeeting(int speakerId) {
        return proposalDataDao.findActiveSpeakerProposalsOrderByMeeting(speakerId);
    }
}
