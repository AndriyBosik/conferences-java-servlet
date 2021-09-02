package com.conferences.dao.abstraction;

import com.conferences.entity.Meeting;
import com.conferences.model.MeetingSorter;
import com.conferences.model.Page;
import com.conferences.model.PageResponse;

public interface IMeetingDao extends IDao<Integer, Meeting> {

    Meeting findByKeyWithReportTopicsAndSpeakersAndUsersCount(Integer key);

    PageResponse<Meeting> findAllPageBySorterWithUsersCountAndTopicsCount(Page page, MeetingSorter sorter);

    PageResponse<Meeting> findAllSpeakerMeetingsPageBySorterWithUsersCountAndTopicsCount(Page page, MeetingSorter sorter, int speakerId);

    boolean updateMeetingEditableData(Meeting meeting);

}
