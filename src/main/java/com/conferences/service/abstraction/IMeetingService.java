package com.conferences.service.abstraction;

import com.conferences.entity.Meeting;

import java.util.List;

public interface IMeetingService {

    List<Meeting> getAllMeetings();

}
