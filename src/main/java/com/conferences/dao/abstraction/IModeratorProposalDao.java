package com.conferences.dao.abstraction;

import com.conferences.entity.ModeratorProposal;

public interface IModeratorProposalDao extends ICrudDao<Integer, ModeratorProposal> {

    boolean deleteProposal(ModeratorProposal moderatorProposal);

}
