package com.conferences.service.implementation;

import com.conferences.dao.abstraction.IMeetingDao;
import com.conferences.dao.implementation.MeetingDao;
import com.conferences.entity.Meeting;
import com.conferences.model.Page;
import com.conferences.service.abstraction.IMeetingService;

import java.util.List;

public class MeetingService implements IMeetingService {

    private IMeetingDao meetingDao;

    public MeetingService() {
        this.meetingDao = new MeetingDao();
    }

    @Override
    public List<Meeting> getAllMeetingsByPage(Page page) {
        return meetingDao.findAllPage(page).getItem();
    }

    @Override
    public Meeting getMeetingWithTopicsAndSpeakers(int id) {
        return meetingDao.findByKeyWithReportTopicsAndSpeakers(id);
    }

}
