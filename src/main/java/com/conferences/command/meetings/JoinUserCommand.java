package com.conferences.command.meetings;

import com.conferences.command.FrontCommand;
import com.conferences.config.Defaults;
import com.conferences.config.ErrorKey;
import com.conferences.config.FormKeys;
import com.conferences.config.Page;
import com.conferences.entity.Meeting;
import com.conferences.entity.User;
import com.conferences.factory.ServiceFactory;
import com.conferences.model.FormError;
import com.conferences.service.abstraction.IMeetingService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *     Responds to /meetings/join-user page requests
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public class JoinUserCommand extends FrontCommand {

    private IMeetingService meetingService;

    @Override
    public void init(ServletContext context, HttpServletRequest request, HttpServletResponse response, List<String> urlParams) throws IOException {
        super.init(context, request, response, urlParams);
        meetingService = ServiceFactory.getInstance().getMeetingService();
    }

    /**
     * <p>
     *     Tries to join user for meeting. Redirects to /meetings/show/{id} page if fails
     * </p>
     * @throws ServletException an exception which may occur during saving errors to session
     * @throws IOException an exception which may occur during saving errors to session
     */
    @Override
    public void process() throws ServletException, IOException {
        int meetingId = Integer.parseInt(request.getParameter("meeting_id"));
        Meeting meeting = meetingService.getMeetingById(meetingId);

        if (meeting.isOutdated()) {
            redirect(Page.MEETING.getUrl() + meetingId);
            return;
        }

        User user = (User) request.getSession().getAttribute(Defaults.USER.toString());
        if (!meetingService.joinUser(meetingId, user.getId())) {
            List<FormError> errors = new ArrayList<>();
            errors.add(new FormError(ErrorKey.JOINING_ERROR));
            saveErrorsToSession(FormKeys.JOINING_ERRORS, errors);
        }
        redirect(Page.MEETING.getUrl() + meetingId);
    }
}
