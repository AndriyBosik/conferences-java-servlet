package com.conferences.dao.abstraction;

import com.conferences.entity.ModeratorProposal;

/**
 * <p>
 *     Defines methods to process moderator_proposals table
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public interface IModeratorProposalDao extends ICrudDao<Integer, ModeratorProposal> {

    /**
     * <p>
     *     Deletes moderator proposal
     * </p>
     * @param moderatorProposal entity which has to be deleted
     * @return true if entity was successfully deleted, false otherwise
     */
    boolean deleteProposal(ModeratorProposal moderatorProposal);
}
