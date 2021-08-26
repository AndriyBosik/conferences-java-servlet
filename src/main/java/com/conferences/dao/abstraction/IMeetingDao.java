package com.conferences.dao.abstraction;

import com.conferences.entity.Meeting;

public interface IMeetingDao extends IDao<Integer, Meeting> {

    Meeting findByKeyWithReportTopicsAndSpeakers(Integer key);

}
