package com.conferences.service.abstraction;

import com.conferences.entity.ModeratorProposal;
import com.conferences.entity.User;

import java.util.List;

public interface IModeratorProposalService {

    boolean saveModeratorProposal(ModeratorProposal moderatorProposal);

    List<User> getAvailableSpeakersForProposalByTopic(int topicId);

    boolean deleteModeratorProposal(ModeratorProposal moderatorProposal);

}
