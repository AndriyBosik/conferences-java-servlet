package com.conferences.validator;

import com.conferences.entity.Meeting;

import java.util.Date;

public class MeetingValidator implements IValidator<Meeting> {

    @Override
    public boolean isValid(Meeting model) {
        return  model.getTitle() != null && model.getTitle().length() >= 5 &&
                model.getDescription() != null && model.getDescription().length() >= 10 &&
                model.getDate() != null && model.getDate().after(new Date()) &&
                model.getImagePath() != null && model.getImagePath().length() > 0;
    }
}
