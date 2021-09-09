package com.conferences.dao.abstraction;

import com.conferences.entity.UserMeeting;

import java.util.List;

/**
 * <p>
 *     Defines methods to process users_meetings table
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public interface IUserMeetingDao extends ICrudDao<Integer, UserMeeting> {

    /**
     * <p>
     *     Retrieves {@link UserMeeting} entity from database by {@code userId} and {@code meetingId}
     * </p>
     * @param userId id of user
     * @param meetingId id of meeting
     * @return found {@link UserMeeting} object and {@code null} if no object was found
     */
    UserMeeting findByUserIdAndMeetingId(int userId, int meetingId);

    /**
     * <p>
     *     Retrieves list of {@link UserMeeting} objects with users and their presence for specific meeting
     * </p>
     * @param meetingId id of meeting to retrieve list of {@link UserMeeting} objects for
     * @return list of {@link UserMeeting}
     */
    List<UserMeeting> findUsersWithPresenceByMeetingId(int meetingId);

    /**
     * <p>
     *     Updates user's presence for meeting
     * </p>
     * @param userMeeting data about user, meeting and user's presence
     * @return true if presence was successfully updated, false otherwise
     */
    boolean updateUserPresenceByUserIdAndMeetingId(UserMeeting userMeeting);
}
