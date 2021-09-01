package com.conferences.service.implementation;

import com.conferences.dao.abstraction.IProposedTopicDataDao;
import com.conferences.dao.implementation.ProposedTopicDataDao;
import com.conferences.entity.custom.ProposedTopicData;
import com.conferences.service.abstraction.IProposedTopicDataService;

import java.util.List;

public class ProposedTopicDataService implements IProposedTopicDataService {

    private final IProposedTopicDataDao proposedTopicDataDao;

    public ProposedTopicDataService() {
        proposedTopicDataDao = new ProposedTopicDataDao();
    }

    @Override
    public List<ProposedTopicData> getProposedTopicsOrderByMeeting() {
        return proposedTopicDataDao.findAllProposedTopicsOrderByMeetingId();
    }
}
