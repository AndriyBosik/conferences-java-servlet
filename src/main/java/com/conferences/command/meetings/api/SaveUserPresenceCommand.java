package com.conferences.command.meetings.api;

import com.conferences.command.JsonApiCommand;
import com.conferences.config.Defaults;
import com.conferences.config.ErrorKey;
import com.conferences.entity.UserMeeting;
import com.conferences.factory.ServiceFactory;
import com.conferences.model.FormError;
import com.conferences.model.SimpleResponse;
import com.conferences.service.abstraction.IMeetingService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class SaveUserPresenceCommand extends JsonApiCommand {

    private IMeetingService meetingService;

    @Override
    public void init(ServletContext context, HttpServletRequest request, HttpServletResponse response, List<String> urlParams) {
        super.init(context, request, response, urlParams);
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
