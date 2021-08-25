package com.conferences.service.implementation;

import com.conferences.dao.abstraction.IMeetingDao;
import com.conferences.dao.implementation.MeetingDao;
import com.conferences.entity.Meeting;
import com.conferences.service.abstraction.IMeetingService;

import java.util.List;

public class MeetingService implements IMeetingService {

    private IMeetingDao meetingDao;

    public MeetingService() {
        this.meetingDao = new MeetingDao();
    }

    @Override
    public List<Meeting> getAllMeetings() {
        return meetingDao.findAll();
    }

}
