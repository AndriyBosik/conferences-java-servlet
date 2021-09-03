package com.conferences.command.meetings.api;

import com.conferences.command.JsonApiCommand;
import com.conferences.entity.UserMeeting;
import com.conferences.factory.ServiceFactory;
import com.conferences.model.SimpleResponse;
import com.conferences.service.abstraction.IMeetingService;
import com.conferences.service.implementation.MeetingService;

public class SaveUserPresenceCommand extends JsonApiCommand {

    private final IMeetingService meetingService;

    public SaveUserPresenceCommand() {
        meetingService = ServiceFactory.getInstance().getMeetingService();
    }

    @Override
    protected Object getJsonObject() {
        UserMeeting userMeeting = jsonHandler.parseJsonRequestBodyToObject(request, UserMeeting.class);
        if (meetingService.updateUserPresence(userMeeting)) {
            return new SimpleResponse("success", "");
        }
        return new SimpleResponse("error", "Error during presence updating");
    }
}
