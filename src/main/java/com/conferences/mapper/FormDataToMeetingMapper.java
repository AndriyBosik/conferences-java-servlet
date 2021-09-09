package com.conferences.mapper;

import com.conferences.config.Defaults;
import com.conferences.entity.Meeting;
import com.conferences.util.TimeUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

/**
 * {@inheritDoc}
 */
public class FormDataToMeetingMapper implements IMapper<Map<String, String>, Meeting> {

    /**
     * <p>
     *     Maps {@code formData} to {@link Meeting}
     * </p>
     * @param formData form data where key is name of field and value is value of that field
     */
    @Override
    public Meeting map(Map<String, String> formData) {
        String strDate =
                formData.get("date") + " " +
                TimeUtil.addZeroToBegin(Integer.parseInt(formData.get("hours"))) + ":" +
                TimeUtil.addZeroToBegin(Integer.parseInt(formData.get("minutes")));
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
