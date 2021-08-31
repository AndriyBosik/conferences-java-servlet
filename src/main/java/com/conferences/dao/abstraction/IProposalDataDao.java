package com.conferences.dao.abstraction;

import com.conferences.entity.custom.ProposalData;

import java.util.List;

public interface IProposalDataDao extends IDao<Integer, ProposalData> {

    List<ProposalData> findActiveModeratorProposalsForSpeakerOrderByMeeting(int speakerId);

    List<ProposalData> findActiveSpeakerProposalsOrderByMeeting(int speakerId);

}
