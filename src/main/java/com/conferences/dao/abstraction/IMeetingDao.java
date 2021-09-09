package com.conferences.dao.abstraction;

import com.conferences.entity.Meeting;
import com.conferences.model.MeetingSorter;
import com.conferences.model.Page;
import com.conferences.model.PageResponse;

/**
 * <p>
 *     Defines methods to process meetings table
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public interface IMeetingDao extends ICrudDao<Integer, Meeting> {

    /**
     * <p>
     *     Retrieves {@link Meeting} from database with its topics(and their speakers) and users count
     * </p>
     * @param key id of {@link Meeting}
     * @return {@link Meeting} with its data
     */
    Meeting findByKeyWithReportTopicsAndSpeakersAndUsersCount(Integer key);

    /**
     * <p>
     *     Retrieves sorted and filtered list of {@link Meeting} objects with their users count and topics count for specific page
     * </p>
     * @param page model which contains data about needed page
     * @param sorter model which contains sort and filter options
     * @return {@link PageResponse} model which contains data about current page response
     */
    PageResponse<Meeting> findAllPageBySorterWithUsersCountAndTopicsCount(Page page, MeetingSorter sorter);

    /**
     * <p>
     *     Retrieves sorted and filtered list of {@link Meeting} objects with their users count and topics count for specific page in which specified speaker took part
     * </p>
     * @param page model which contains data about needed page
     * @param sorter model which contains sort and filter options
     * @param speakerId id of specified speaker
     * @return {@link PageResponse} model which contains data about current page response
     */
    PageResponse<Meeting> findAllSpeakerMeetingsPageBySorterWithUsersCountAndTopicsCount(Page page, MeetingSorter sorter, int speakerId);

    /**
     * <p>
     * </p>
     *     Updates meeting data that moderator can edit
     * @param meeting entity which contains edited data
     * @return true if data was successfully updated, false otherwise
     */
    boolean updateMeetingEditableData(Meeting meeting);
}
