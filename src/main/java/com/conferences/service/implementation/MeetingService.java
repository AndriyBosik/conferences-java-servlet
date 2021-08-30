package com.conferences.service.implementation;

import com.conferences.dao.abstraction.IMeetingDao;
import com.conferences.dao.abstraction.IUserMeetingDao;
import com.conferences.dao.implementation.MeetingDao;
import com.conferences.dao.implementation.UserMeetingDao;
import com.conferences.entity.Meeting;
import com.conferences.entity.UserMeeting;
import com.conferences.model.MeetingSorter;
import com.conferences.model.Page;
import com.conferences.model.PageResponse;
import com.conferences.service.abstraction.IMeetingService;
import com.conferences.validator.IValidator;
import com.conferences.validator.MeetingValidator;

import java.util.List;

public class MeetingService implements IMeetingService {

    private final IMeetingDao meetingDao;
    private final IUserMeetingDao userMeetingDao;
    private final IValidator<Meeting> meetingValidator;

    public MeetingService() {
        this.meetingDao = new MeetingDao();
        this.userMeetingDao = new UserMeetingDao();
        this.meetingValidator = new MeetingValidator();
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
}
