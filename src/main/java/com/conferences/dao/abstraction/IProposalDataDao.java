package com.conferences.dao.abstraction;

import com.conferences.entity.custom.ProposalData;

import java.util.List;

/**
 * <p>
 *     Defines method to process proposals
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public interface IProposalDataDao extends IDao<Integer, ProposalData> {

    /**
     * <p>
     *     Finds moderator proposals for speaker and topics those do not have assigned speaker yet
     * </p>
     * @param speakerId id of speaker
     * @return list of {@link ProposalData}
     */
    List<ProposalData> findActiveModeratorProposalsForSpeakerOrderByMeeting(int speakerId);

    /**
     * <p>
     *     Finds speaker proposals for topics those do not have assigned speaker yet
     * </p>
     * @param speakerId id of speaker
     * @return list of {@link ProposalData}
     */
    List<ProposalData> findActiveSpeakerProposalsOrderByMeeting(int speakerId);
}
