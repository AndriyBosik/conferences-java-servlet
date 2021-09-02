package com.conferences.mapper;

import com.conferences.config.Defaults;
import com.conferences.entity.Meeting;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class FormDataToMeetingMapper implements IMapper<Map<String, String>, Meeting> {

    @Override
    public Meeting map(Map<String, String> formData) {
        String strDate = formData.get("date") + " " + formData.get("hours") + ":" + formData.get("minutes");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Defaults.DATE_FORMAT.toString(), Locale.UK);
        LocalDateTime date = LocalDateTime.parse(strDate, formatter);

        Meeting meeting = new Meeting();

        meeting.setTitle(formData.get("title"));
        meeting.setDescription(formData.get("description"));
        meeting.setAddress(formData.get("address"));
        meeting.setImagePath(formData.get("image_path"));
        meeting.setDate(date);

        return meeting;
    }
}
