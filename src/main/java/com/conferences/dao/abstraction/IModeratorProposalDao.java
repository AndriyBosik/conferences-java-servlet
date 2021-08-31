package com.conferences.dao.abstraction;

import com.conferences.entity.ModeratorProposal;

public interface IModeratorProposalDao extends IDao<Integer, ModeratorProposal> {

    boolean deleteProposal(ModeratorProposal moderatorProposal);

}
