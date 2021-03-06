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
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *     Responds to /meetings-api/save-user-presence page requests
 * </p>
 */
public class SaveUserPresenceCommand extends JsonApiCommand {

    private IMeetingService meetingService;

    @Override
    public void init(ServletContext context, HttpServletRequest request, HttpServletResponse response, List<String> urlParams) throws IOException {
        super.init(context, request, response, urlParams);
        meetingService = ServiceFactory.getInstance().getMeetingService();
    }

    /**
     * <p>
     *     Tries to save user presence received as JSON object from request body and sends JSON response
     * </p>
     * @return
     */
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
