package com.conferences.command.topics;

import com.conferences.command.FrontCommand;
import com.conferences.config.*;
import com.conferences.entity.User;
import com.conferences.factory.ServiceFactory;
import com.conferences.model.FormError;
import com.conferences.service.abstraction.ISpeakerService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProposeSpeakerCommand extends FrontCommand {

    private ISpeakerService speakerService;

    @Override
    public void init(ServletContext context, HttpServletRequest request, HttpServletResponse response, List<String> urlParams) {
        super.init(context, request, response, urlParams);
        speakerService = ServiceFactory.getInstance().getSpeakerService();
    }

    @Override
    public void process() throws ServletException, IOException {
        if (!request.getMethod().equals(HttpMethod.POST.toString())) {
            return;
        }
        User user = (User) request.getSession().getAttribute(Defaults.USER.toString());
        int userId = user.getId();
        int reportTopicId = Integer.parseInt(request.getParameter("report_topic_id"));
        int meetingId = Integer.parseInt(request.getParameter("meeting_id"));

        if (!speakerService.proposeSpeaker(reportTopicId, userId)) {
            List<FormError> errors = new ArrayList<>();
            errors.add(new FormError(ErrorKey.PROPOSE_SPEAKER_ERROR));
            saveErrorsToSession(FormKeys.SPEAKER_PROPOSAL_ERRORS, errors);
        }
        redirect(Page.MEETING.getUrl() + meetingId);
    }
}
