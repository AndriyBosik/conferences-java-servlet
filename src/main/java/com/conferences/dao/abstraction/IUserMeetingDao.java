package com.conferences.dao.abstraction;

import com.conferences.entity.UserMeeting;

import java.util.List;

public interface IUserMeetingDao extends ICrudDao<Integer, UserMeeting> {

    UserMeeting findByUserIdAndMeetingId(int userId, int meetingId);

    List<UserMeeting> findUserWithPresenceByMeetingId(int meetingId);

    boolean updateUserPresenceByUserIdAndMeetingId(UserMeeting userMeeting);

}
