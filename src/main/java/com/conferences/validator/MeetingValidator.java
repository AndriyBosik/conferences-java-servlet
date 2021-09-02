package com.conferences.validator;

import com.conferences.entity.Meeting;

import java.time.LocalDateTime;
import java.util.Date;

public class MeetingValidator implements IValidator<Meeting> {

    @Override
    public boolean isValid(Meeting model) {
        return  model.getTitle() != null && model.getTitle().trim().length() >= 5 &&
                model.getDescription() != null && model.getDescription().trim().length() >= 10 &&
                model.getAddress() != null && model.getAddress().trim().length() >= 5 &&
                model.getDate() != null && model.getDate().isAfter(LocalDateTime.now()) &&
                model.getImagePath() != null && model.getImagePath().length() > 0;
    }
}
