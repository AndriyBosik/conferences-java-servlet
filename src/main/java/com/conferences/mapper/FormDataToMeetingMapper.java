package com.conferences.mapper;

import com.conferences.config.Defaults;
import com.conferences.entity.Meeting;
import com.conferences.handler.abstraction.IEncodingHandler;
import com.conferences.handler.implementation.EncodingHandler;
import com.conferences.utils.TimeUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

public class FormDataToMeetingMapper implements IMapper<Map<String, String>, Meeting> {

    private final IEncodingHandler encodingHandler;

    public FormDataToMeetingMapper() {
        encodingHandler = new EncodingHandler();
    }

    @Override
    public Meeting map(Map<String, String> formData) {
        String strDate =
                formData.get("date") + " " +
                TimeUtils.addZeroToBegin(formData.get("hours")) + ":" +
                TimeUtils.addZeroToBegin(formData.get("minutes"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Defaults.DATE_FORMAT.toString(), Locale.UK);
        LocalDateTime date = LocalDateTime.parse(strDate, formatter);

        Meeting meeting = new Meeting();

        meeting.setTitle(encodingHandler.getUTF8ValueFromFormDataMap(formData, "title"));
        meeting.setDescription(encodingHandler.getUTF8ValueFromFormDataMap(formData, "description"));
        meeting.setAddress(encodingHandler.getUTF8ValueFromFormDataMap(formData, "address"));
        meeting.setImagePath(encodingHandler.getUTF8ValueFromFormDataMap(formData, "image_path"));
        meeting.setDate(date);

        return meeting;
    }
}
