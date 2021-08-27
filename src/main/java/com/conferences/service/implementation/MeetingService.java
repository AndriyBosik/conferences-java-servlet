package com.conferences.service.implementation;

import com.conferences.dao.abstraction.IMeetingDao;
import com.conferences.dao.implementation.MeetingDao;
import com.conferences.entity.Meeting;
import com.conferences.model.Page;
import com.conferences.service.abstraction.IMeetingService;
import com.conferences.validator.IValidator;
import com.conferences.validator.MeetingValidator;

import java.util.List;

public class MeetingService implements IMeetingService {

    private IMeetingDao meetingDao;
    private IValidator<Meeting> meetingValidator;

    public MeetingService() {
        this.meetingDao = new MeetingDao();
        meetingValidator = new MeetingValidator();
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
            meetingDao.create(meeting);
            return true;
        }
        return false;
    }

}
