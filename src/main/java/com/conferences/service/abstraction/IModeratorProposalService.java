package com.conferences.service.abstraction;

import com.conferences.entity.ModeratorProposal;
import com.conferences.entity.User;

import java.util.List;

/**
 * <p>
 *     Defines functions which may be used to process {@link ModeratorProposal} data
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public interface IModeratorProposalService {

    /**
     * <p>
     *     Saves moderatorProposal
     * </p>
     * @param moderatorProposal proposal that should be saved
     * @return true if proposal was saved, false otherwise
     */
    boolean saveModeratorProposal(ModeratorProposal moderatorProposal);

    /**
     * <p>
     *     Gets speakers that are available for topic to propose
     * </p>
     * @param topicId id of topic to find available speakers for
     * @return list of available speakers
     */
    List<User> getAvailableSpeakersForProposalByTopic(int topicId);

    /**
     * <p>
     *     Deletes moderator proposal
     * </p>
     * @param moderatorProposal proposal that must be deleted
     * @return true if proposal was successfully deleted, false otherwise
     */
    boolean deleteModeratorProposal(ModeratorProposal moderatorProposal);
}
