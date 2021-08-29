package com.conferences.command.topics;

import com.conferences.command.FrontCommand;
import com.conferences.config.HttpMethod;
import com.conferences.config.Pages;
import com.conferences.entity.ReportTopic;
import com.conferences.service.abstraction.IReportTopicService;
import com.conferences.service.implementation.ReportTopicService;

import javax.servlet.ServletException;
import java.io.IOException;

public class UpdateCommand extends FrontCommand {

    private final IReportTopicService reportTopicService;

    public UpdateCommand() {
        reportTopicService = new ReportTopicService();
    }

    @Override
    public void process() throws ServletException, IOException {
        if (!request.getMethod().equals(HttpMethod.POST.toString())) {
            return;
        }

        String speakerIdParameter = request.getParameter("speaker_id");

        ReportTopic reportTopic = new ReportTopic();
        reportTopic.setId(Integer.parseInt(request.getParameter("id")));
        reportTopic.setTitle(request.getParameter("title"));
        reportTopic.setMeetingId(Integer.parseInt(request.getParameter("meeting_id")));
        if (speakerIdParameter != null && !speakerIdParameter.trim().isEmpty()) {
            reportTopic.setSpeakerId(Integer.parseInt(request.getParameter("speaker_id")));
        } else {
            reportTopic.setSpeakerId(null);
        }

        if (reportTopicService.update(reportTopic)) {
            redirect(Pages.MEETING.getUrl() + reportTopic.getMeetingId());
        } else {
            // Process request error
        }
    }
}
