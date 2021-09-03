package com.conferences.factory;

import com.conferences.entity.Meeting;
import com.conferences.entity.ReportTopic;
import com.conferences.entity.User;
import com.conferences.validator.*;

public class ValidatorFactory {

    private static ValidatorFactory instance;

    private IValidator<Meeting> meetingEditableDataValidator;
    private IValidator<Meeting> meetingValidator;
    private IValidator<ReportTopic> reportTopicValidator;
    private IValidator<User> userRequiredForUpdateDataValidator;
    private IValidator<User> userValidator;

    private ValidatorFactory() {}

    public static synchronized ValidatorFactory getInstance() {
        if (instance == null) {
            instance = new ValidatorFactory();
        }
        return instance;
    }

    public synchronized IValidator<Meeting> getMeetingEditableDataValidator() {
        if (meetingEditableDataValidator == null) {
            meetingEditableDataValidator = new MeetingEditableDataValidator();
        }
        return meetingEditableDataValidator;
    }

    public synchronized IValidator<Meeting> getMeetingValidator() {
        if (meetingValidator == null) {
            meetingValidator = new MeetingValidator();
        }
        return meetingValidator;
    }

    public synchronized IValidator<ReportTopic> getReportTopicValidator() {
        if (reportTopicValidator == null) {
            reportTopicValidator = new ReportTopicValidator();
        }
        return reportTopicValidator;
    }

    public synchronized IValidator<User> getUserRequiredForUpdateDataValidator() {
        if (userRequiredForUpdateDataValidator == null) {
            userRequiredForUpdateDataValidator = new UserRequiredForUpdateDataValidator();
        }
        return userRequiredForUpdateDataValidator;
    }

    public synchronized IValidator<User> getUserValidator() {
        if (userValidator == null) {
            userValidator = new UserValidator();
        }
        return userValidator;
    }
}
