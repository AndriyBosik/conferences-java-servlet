package com.conferences.service.implementation;

import com.conferences.dao.abstraction.IMeetingDao;
import com.conferences.dao.abstraction.IUserMeetingDao;
import com.conferences.dao.implementation.MeetingDao;
import com.conferences.dao.implementation.UserMeetingDao;
import com.conferences.entity.Meeting;
import com.conferences.entity.UserMeeting;
import com.conferences.model.Page;
import com.conferences.service.abstraction.IMeetingService;
import com.conferences.validator.IValidator;
import com.conferences.validator.MeetingValidator;

import java.util.List;

public class MeetingService implements IMeetingService {

    private IMeetingDao meetingDao;
    private IUserMeetingDao userMeetingDao;
    private IValidator<Meeting> meetingValidator;

    public MeetingService() {
        this.meetingDao = new MeetingDao();
        this.userMeetingDao = new UserMeetingDao();
        this.meetingValidator = new MeetingValidator();
    }

    @Override
    public List<Meeting> getAllMeetingsByPage(Page page) {
        return meetingDao.findAllPage(page).getItem();
    }

    @Override
    public Meeting getMeetingWithTopicsAndSpeakers(int id) {
        return meetingDao.findByKeyWithReportTopicsAndSpeakers(id);
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
}
