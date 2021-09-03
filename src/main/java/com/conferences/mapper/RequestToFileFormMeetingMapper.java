package com.conferences.mapper;

import com.conferences.entity.Meeting;
import com.conferences.factory.MapperFactory;

import java.util.Map;

public class RequestToFileFormMeetingMapper extends AbstractFileFormMapper<Meeting> {

    private final IMapper<Map<String, String>, Meeting> meetingMapper;

    public RequestToFileFormMeetingMapper() {
        meetingMapper = MapperFactory.getInstance().getFormDataToMeetingMapper();
    }

    @Override
    protected Meeting mapFormDataToReturnValue(Map<String, String> formData) {
        return meetingMapper.map(formData);
    }
}
