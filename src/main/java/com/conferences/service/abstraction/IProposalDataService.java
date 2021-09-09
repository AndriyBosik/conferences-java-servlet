package com.conferences.service.abstraction;

import com.conferences.entity.custom.ProposalData;

import java.util.List;

/**
 * <p>
 *     Defines function to get {@link ProposalData}
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public interface IProposalDataService {

    /**
     * <p>
     *     Gets moderator proposals for speaker and topics those do not have assigned speaker yet
     * </p>
     * @param speakerId id of speaker to get proposals for
     * @return list of {@link ProposalData}
     */
    List<ProposalData> getActiveModeratorProposalsForSpeakerOrderByMeeting(int speakerId);

    /**
     * <p>
     *     Gets speaker proposals for topics those do not have assigned speaker yet
     * </p>
     * @param speakerId id of speaker to get proposals from
     * @return list of {@link ProposalData}
     */
    List<ProposalData> getActiveSpeakerProposalsOrderByMeeting(int speakerId);
}
