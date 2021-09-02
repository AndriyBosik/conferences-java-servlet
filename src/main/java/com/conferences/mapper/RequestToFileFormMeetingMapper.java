package com.conferences.mapper;

import com.conferences.entity.Meeting;

import java.util.Map;

public class RequestToFileFormMeetingMapper extends AbstractFileFormMapper<Meeting> {

    private final IMapper<Map<String, String>, Meeting> meetingMapper;

    public RequestToFileFormMeetingMapper() {
        meetingMapper = new FormDataToMeetingMapper();
    }

    @Override
    protected Meeting mapFormDataToReturnValue(Map<String, String> formData) {
        return meetingMapper.map(formData);
    }
}
