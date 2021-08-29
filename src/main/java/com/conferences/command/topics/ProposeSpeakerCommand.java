package com.conferences.command.topics;

import com.conferences.command.FrontCommand;
import com.conferences.config.Defaults;
import com.conferences.config.HttpMethod;
import com.conferences.config.Pages;
import com.conferences.entity.User;
import com.conferences.service.abstraction.ISpeakerService;
import com.conferences.service.implementation.SpeakerService;

import javax.servlet.ServletException;
import java.io.IOException;

public class ProposeSpeakerCommand extends FrontCommand {

    private ISpeakerService speakerService;

    public ProposeSpeakerCommand() {
        speakerService = new SpeakerService();
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

        if (speakerService.proposeSpeaker(reportTopicId, userId)) {
            redirect(Pages.MEETING.getUrl() + meetingId);
        } else {
            // TODO
        }
    }
}
