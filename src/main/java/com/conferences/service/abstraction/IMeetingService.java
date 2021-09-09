package com.conferences.service.abstraction;

import com.conferences.entity.Meeting;
import com.conferences.entity.UserMeeting;
import com.conferences.model.*;

import java.util.List;

/**
 * <p>
 *     Defines functions which may be used to extract/update {@link Meeting} data
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public interface IMeetingService {

    /**
     * <p>
     *     Returns {@link PageResponse} containing {@link Meeting} objects representing current page
     * </p>
     * @param page - page data
     * @param sorter - sort and filter options
     * @return {@link PageResponse}
     */
    PageResponse<Meeting> getAllMeetingsByPageAndSorterWithUsersCountAndTopicsCount(Page page, MeetingSorter sorter);

    /**
     * <p>
     *     Gets object of {@link Meeting} class with its topics count and users count
     * </p>
     * @param id id of meeting object
     * @return {@link Meeting} object with its data
     */
    Meeting getMeetingWithTopicsAndSpeakersAndUsersCount(int id);

    /**
     * <p>
     *     Tries to save meeting to database
     * </p>
     * @param meeting meeting needed to be saves to database
     * @return list of errors if there was such during saving
     */
    List<FormError> saveMeeting(Meeting meeting);

    /**
     * <p>
     *     Joins user to meeting
     * </p>
     * @param meetingId id of meeting which user wants to join to
     * @param userId id of user that wants to join to meeting
     * @return true if user was joined, false otherwise
     */
    boolean joinUser(int meetingId, int userId);

    /**
     * <p>
     *     Checks whatever user has been joined to specified meeting
     * </p>
     * @param meetingId id of meeting that must be checked
     * @param userId id of user that must be checked
     * @return true if user is joined to meeting, false otherwise
     */
    boolean hasJoinedUser(int meetingId, int userId);

    /**
     * <p>
     *     Gets meeting by id
     * </p>
     * @param meetingId id of meeting
     * @return {@link Meeting}
     */
    Meeting getMeetingById(int meetingId);

    /**
     * <p>
     *     Updates meeting data that moderator can edit
     * </p>
     * @param meeting meeting with edited data
     * @return list of errors those may occur while meeting updating
     */
    List<FormError> updateMeetingEditableData(Meeting meeting);

    /**
     * <p>
     *     Returns {@link PageResponse} containing {@link Meeting} object which specified speaker took part in
     * </p>
     * @param page page data
     * @param sorter sort and filter options
     * @param speakerId id of speaker
     * @return {@link PageResponse}
     */
    PageResponse<Meeting> getSpeakerMeetings(Page page, MeetingSorter sorter, int speakerId);

    /**
     * <p>
     *     Gets users with presence for specified meeting
     * </p>
     * @param meetingId id of meeting to get presented users from
     * @return {@link MeetingUsersStats}
     */
    MeetingUsersStats getUsersWithPresenceByMeeting(int meetingId);

    /**
     * <p>
     *     Updates user presence
     * </p>
     * @param userMeeting model containing data about meeting, user and presence
     * @return true if presence was updated, false otherwise
     */
    boolean updateUserPresence(UserMeeting userMeeting);
}
