package com.conferences.dao.abstraction;

import com.conferences.entity.Meeting;
import com.conferences.model.Page;
import com.conferences.model.PageResponse;

import java.util.List;

public interface IMeetingDao extends IDao<Integer, Meeting> {

    Meeting findByKeyWithReportTopicsAndSpeakers(Integer key);

    PageResponse<List<Meeting>> findAllPage(Page page);

}
