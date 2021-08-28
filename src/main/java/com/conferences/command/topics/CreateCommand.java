package com.conferences.command.topics;

import com.conferences.command.FrontCommand;
import com.conferences.config.HttpMethod;
import com.conferences.config.Pages;
import com.conferences.entity.ReportTopic;
import com.conferences.service.abstraction.IReportTopicService;
import com.conferences.service.implementation.ReportTopicService;

import javax.servlet.ServletException;
import java.io.IOException;

public class CreateCommand extends FrontCommand {

    private IReportTopicService reportTopicService;

    public CreateCommand() {
        reportTopicService = new ReportTopicService();
    }

    @Override
    public void process() throws ServletException, IOException {
        if (!request.getMethod().equals(HttpMethod.POST.toString())) {
            return;
        }

        ReportTopic reportTopic = new ReportTopic();
        reportTopic.setTitle(request.getParameter("title"));
        reportTopic.setMeetingId(Integer.parseInt(request.getParameter("meeting_id")));
        reportTopic.setSpeakerId(Integer.parseInt(request.getParameter("speaker_id")));

        if (reportTopicService.save(reportTopic)) {
            redirect(Pages.MEETING.getUrl() + reportTopic.getMeetingId());
        } else {
            // Process saving error
        }
    }
}