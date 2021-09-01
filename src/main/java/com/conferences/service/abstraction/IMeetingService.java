package com.conferences.service.abstraction;

import com.conferences.config.MeetingFilterSelector;
import com.conferences.entity.Meeting;
import com.conferences.model.MeetingSorter;
import com.conferences.model.Page;
import com.conferences.model.PageResponse;

import java.util.List;

public interface IMeetingService {

    PageResponse<Meeting> getAllMeetingsByPageAndSorterWithUsersCountAndTopicsCount(Page page, MeetingSorter sorter);

    Meeting getMeetingWithTopicsAndSpeakersAndUsersCount(int id);

    boolean saveMeeting(Meeting meeting);

    boolean joinUser(int meetingId, int userId);

    boolean hasJoinedUser(int meetingId, int userId);

    Meeting getMeetingById(int meetingId);

    boolean updateMeetingEditableData(Meeting meeting);

    PageResponse<Meeting> getSpeakerMeetings(Page page, MeetingSorter sorter, int speakerId);

}
