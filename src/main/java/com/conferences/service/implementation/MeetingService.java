package com.conferences.service.implementation;

import com.conferences.config.ErrorKey;
import com.conferences.dao.abstraction.IMeetingDao;
import com.conferences.dao.abstraction.IUserMeetingDao;
import com.conferences.entity.Meeting;
import com.conferences.entity.UserMeeting;
import com.conferences.factory.DaoFactory;
import com.conferences.factory.ValidatorFactory;
import com.conferences.model.*;
import com.conferences.service.abstraction.IMeetingService;
import com.conferences.validator.IValidator;

import java.util.List;

/**
 * {@inheritDoc}
 */
public class MeetingService implements IMeetingService {

    private final IMeetingDao meetingDao;
    private final IUserMeetingDao userMeetingDao;
    private final IValidator<Meeting> meetingValidator;
    private final IValidator<Meeting> meetingEditableDataValidator;

    public MeetingService() {
        this.meetingDao = DaoFactory.getInstance().getMeetingDao();
        this.userMeetingDao = DaoFactory.getInstance().getUserMeetingDao();
        this.meetingValidator = ValidatorFactory.getInstance().getMeetingValidator();
        this.meetingEditableDataValidator = ValidatorFactory.getInstance().getMeetingEditableDataValidator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<Meeting> getAllMeetingsByPageAndSorterWithUsersCountAndTopicsCount(Page page, MeetingSorter sorter) {
        return meetingDao.findAllPageBySorterWithUsersCountAndTopicsCount(page, sorter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Meeting getMeetingWithTopicsAndSpeakersAndUsersCount(int id) {
        return meetingDao.findByKeyWithReportTopicsAndSpeakersAndUsersCount(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FormError> saveMeeting(Meeting meeting) {
        List<FormError> errors = meetingValidator.validate(meeting);
        if (errors.isEmpty() && !meetingDao.create(meeting)) {
            errors.add(new FormError(ErrorKey.CREATION_ERROR));
        }
        return errors;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean joinUser(int meetingId, int userId) {
        UserMeeting userMeeting = new UserMeeting();
        userMeeting.setUserId(userId);
        userMeeting.setMeetingId(meetingId);
        return userMeetingDao.create(userMeeting);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasJoinedUser(int meetingId, int userId) {
        return userMeetingDao.findByUserIdAndMeetingId(userId, meetingId) != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Meeting getMeetingById(int meetingId) {
        return meetingDao.find(meetingId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FormError> updateMeetingEditableData(Meeting meeting) {
        List<FormError> errors = meetingEditableDataValidator.validate(meeting);
        if (errors.isEmpty() && !meetingDao.updateMeetingEditableData(meeting)) {
            errors.add(new FormError(ErrorKey.UPDATING_ERROR));
        }
        return errors;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<Meeting> getSpeakerMeetings(Page page, MeetingSorter sorter, int speakerId) {
        return meetingDao.findAllSpeakerMeetingsPageBySorterWithUsersCountAndTopicsCount(page, sorter, speakerId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MeetingUsersStats getUsersWithPresenceByMeeting(int meetingId) {
        return new MeetingUsersStats(userMeetingDao.findUsersWithPresenceByMeetingId(meetingId));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateUserPresence(UserMeeting userMeeting) {
        return userMeetingDao.updateUserPresenceByUserIdAndMeetingId(userMeeting);
    }
}
