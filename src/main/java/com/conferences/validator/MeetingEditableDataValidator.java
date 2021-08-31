package com.conferences.validator;

import com.conferences.entity.Meeting;

import java.util.Date;

public class MeetingEditableDataValidator implements IValidator<Meeting> {

    @Override
    public boolean isValid(Meeting model) {
        return  model.getAddress() != null && model.getAddress().trim().length() >= 5 &&
                model.getDate() != null && model.getDate().after(new Date());
    }
}
