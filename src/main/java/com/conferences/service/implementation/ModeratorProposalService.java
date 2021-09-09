package com.conferences.service.implementation;

import com.conferences.dao.abstraction.IModeratorProposalDao;
import com.conferences.dao.abstraction.IUserDao;
import com.conferences.dao.implementation.ModeratorProposalDao;
import com.conferences.dao.implementation.UserDao;
import com.conferences.entity.ModeratorProposal;
import com.conferences.entity.User;
import com.conferences.entity.custom.ProposalData;
import com.conferences.factory.DaoFactory;
import com.conferences.service.abstraction.IModeratorProposalService;

import java.util.List;

/**
 * {@inheritDoc}
 */
public class ModeratorProposalService implements IModeratorProposalService {

    private final IUserDao userDao;
    private final IModeratorProposalDao moderatorProposalDao;

    public ModeratorProposalService() {
        this.userDao = DaoFactory.getInstance().getUserDao();
        this.moderatorProposalDao = DaoFactory.getInstance().getModeratorProposalDao();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean saveModeratorProposal(ModeratorProposal moderatorProposal) {
        return moderatorProposalDao.create(moderatorProposal);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getAvailableSpeakersForProposalByTopic(int topicId) {
        return userDao.findAvailableSpeakersForProposalByTopic(topicId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteModeratorProposal(ModeratorProposal moderatorProposal) {
        return moderatorProposalDao.deleteProposal(moderatorProposal);
    }
}
