package com.conferences.service.abstraction;

import com.conferences.entity.custom.ProposalData;

import java.util.List;

public interface IProposalDataService {

    List<ProposalData> getActiveModeratorProposalsForSpeakerOrderByMeeting(int speakerId);

    List<ProposalData> getActiveSpeakerProposalsOrderByMeeting(int speakerId);

}
