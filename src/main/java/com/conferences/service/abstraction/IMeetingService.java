package com.conferences.service.abstraction;

import com.conferences.entity.Meeting;
import com.conferences.model.Page;

import java.util.List;

public interface IMeetingService {

    List<Meeting> getAllMeetingsByPage(Page page);

    Meeting getMeetingWithTopicsAndSpeakers(int id);

}
