package com.conferences.command.meetings.api;

import com.conferences.command.JsonApiCommand;
import com.conferences.config.Defaults;
import com.conferences.config.ErrorKey;
import com.conferences.entity.UserMeeting;
import com.conferences.factory.ServiceFactory;
import com.conferences.model.FormError;
import com.conferences.model.SimpleResponse;
import com.conferences.service.abstraction.IMeetingService;

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
        FormError error = new FormError((String) request.getAttribute(Defaults.CURRENT_LANG.toString()), ErrorKey.PRESENCE_ERROR);
        return new SimpleResponse("error", errorMapper.map(error));
    }
}
