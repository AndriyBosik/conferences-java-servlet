package com.conferences.mapper;

import com.conferences.entity.Meeting;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class FormDataToMeetingMapper implements IMapper<Map<String, String>, Meeting> {

    @Override
    public Meeting map(Map<String, String> formData) {
        String strDate = formData.get("date") + " " + formData.get("hours") + ":" + formData.get("minutes");
        Date date = null;
        try {
            date = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(strDate);
        } catch (ParseException exception) {
            exception.printStackTrace();
        }

        Meeting meeting = new Meeting();

        meeting.setTitle(formData.get("title"));
        meeting.setDescription(formData.get("description"));
        meeting.setAddress(formData.get("address"));
        meeting.setImagePath(formData.get("image_path"));
        meeting.setDate(date);

        return meeting;
    }
}
