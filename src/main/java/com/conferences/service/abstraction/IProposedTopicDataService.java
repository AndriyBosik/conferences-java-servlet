package com.conferences.service.abstraction;

import com.conferences.entity.custom.ProposedTopicData;

import java.util.List;

public interface IProposedTopicDataService {

    List<ProposedTopicData> getProposedTopicsOrderByMeeting();

}
