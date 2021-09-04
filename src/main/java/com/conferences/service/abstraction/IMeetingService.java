package com.conferences.service.abstraction;

import com.conferences.entity.Meeting;
import com.conferences.entity.UserMeeting;
import com.conferences.model.*;

import java.util.List;
import java.util.Map;

public interface IMeetingService {

    PageResponse<Meeting> getAllMeetingsByPageAndSorterWithUsersCountAndTopicsCount(Page page, MeetingSorter sorter);

    Meeting getMeetingWithTopicsAndSpeakersAndUsersCount(int id);

    List<FormError> saveMeeting(Meeting meeting);

    boolean joinUser(int meetingId, int userId);

    boolean hasJoinedUser(int meetingId, int userId);

    Meeting getMeetingById(int meetingId);

    List<FormError> updateMeetingEditableData(Meeting meeting);

    PageResponse<Meeting> getSpeakerMeetings(Page page, MeetingSorter sorter, int speakerId);

    MeetingUsersStats getUsersWithPresenceByMeeting(int meetingId);

    boolean updateUserPresence(UserMeeting userMeeting);

}
