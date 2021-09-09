package com.conferences.mapper;

import com.conferences.entity.Meeting;
import com.conferences.factory.MapperFactory;

import java.util.Map;

/**
 * {@inheritDoc}
 */
public class RequestToFileFormMeetingMapper extends AbstractFileFormMapper<Meeting> {

    private final IMapper<Map<String, String>, Meeting> meetingMapper;

    public RequestToFileFormMeetingMapper() {
        meetingMapper = MapperFactory.getInstance().getFormDataToMeetingMapper();
    }

    /**
     * <p>
     *     Maps form data to {@link Meeting}
     * </p>
     * @param formData form data where key is name of field and value is value of that field
     */
    @Override
    protected Meeting mapFormDataToReturnValue(Map<String, String> formData) {
        return meetingMapper.map(formData);
    }
}
