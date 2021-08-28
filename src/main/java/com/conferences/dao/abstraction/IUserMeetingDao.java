package com.conferences.dao.abstraction;

import com.conferences.entity.UserMeeting;

public interface IUserMeetingDao extends IDao<Integer, UserMeeting> {

    UserMeeting findByUserIdAndMeetingId(int userId, int meetingId);

}
