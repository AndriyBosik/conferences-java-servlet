package com.conferences.service.implementation;

import com.conferences.dao.abstraction.IMeetingDao;
import com.conferences.dao.abstraction.IUserMeetingDao;
import com.conferences.entity.Meeting;
import com.conferences.entity.UserMeeting;
import com.conferences.factory.DaoFactory;
import com.conferences.factory.ValidatorFactory;
import com.conferences.model.MeetingSorter;
import com.conferences.model.MeetingUsersStats;
import com.conferences.model.Page;
import com.conferences.model.PageResponse;
import com.conferences.service.abstraction.IMeetingService;
import com.conferences.validator.IValidator;

public class MeetingService implements IMeetingService {

    private final IMeetingDao meetingDao;
    private final IUserMeetingDao userMeetingDao;
    private final IValidator<Meeting> meetingValidator;

    public MeetingService() {
        this.meetingDao = DaoFactory.getInstance().getMeetingDao();
        this.userMeetingDao = DaoFactory.getInstance().getUserMeetingDao();
        this.meetingValidator = ValidatorFactory.getInstance().getMeetingValidator();
    }

    @Override
    public PageResponse<Meeting> getAllMeetingsByPageAndSorterWithUsersCountAndTopicsCount(Page page, MeetingSorter sorter) {
        return meetingDao.findAllPageBySorterWithUsersCountAndTopicsCount(page, sorter);
    }

    @Override
    public Meeting getMeetingWithTopicsAndSpeakersAndUsersCount(int id) {
        return meetingDao.findByKeyWithReportTopicsAndSpeakersAndUsersCount(id);
    }

    @Override
    public boolean saveMeeting(Meeting meeting) {
        if (meetingValidator.isValid(meeting)) {
            return meetingDao.create(meeting);
        }
        return false;
    }

    @Override
    public boolean joinUser(int meetingId, int userId) {
        UserMeeting userMeeting = new UserMeeting();
        userMeeting.setUserId(userId);
        userMeeting.setMeetingId(meetingId);
        return userMeetingDao.create(userMeeting);
    }

    @Override
    public boolean hasJoinedUser(int meetingId, int userId) {
        return userMeetingDao.findByUserIdAndMeetingId(userId, meetingId) != null;
    }

    @Override
    public Meeting getMeetingById(int meetingId) {
        return meetingDao.find(meetingId);
    }

    @Override
    public boolean updateMeetingEditableData(Meeting meeting) {
        return meetingDao.updateMeetingEditableData(meeting);
    }

    @Override
    public PageResponse<Meeting> getSpeakerMeetings(Page page, MeetingSorter sorter, int speakerId) {
        return meetingDao.findAllSpeakerMeetingsPageBySorterWithUsersCountAndTopicsCount(page, sorter, speakerId);
    }

    @Override
    public MeetingUsersStats getUsersWithPresenceByMeeting(int meetingId) {
        return new MeetingUsersStats(userMeetingDao.findUserWithPresenceByMeetingId(meetingId));
    }

    @Override
    public boolean updateUserPresence(UserMeeting userMeeting) {
        return userMeetingDao.updateUserPresenceByUserIdAndMeetingId(userMeeting);
    }
}
