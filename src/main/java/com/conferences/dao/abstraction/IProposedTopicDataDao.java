package com.conferences.dao.abstraction;

import com.conferences.entity.custom.ProposedTopicData;

import java.util.List;

public interface IProposedTopicDataDao extends IDao<Integer, ProposedTopicData> {

    List<ProposedTopicData> findAllProposedTopicsOrderByMeetingId();
}
