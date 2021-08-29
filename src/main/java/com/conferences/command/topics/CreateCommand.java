package com.conferences.command.topics;

import com.conferences.command.FrontCommand;
import com.conferences.config.HttpMethod;
import com.conferences.config.Pages;
import com.conferences.entity.ReportTopic;
import com.conferences.entity.ReportTopicSpeaker;
import com.conferences.mapper.IMapper;
import com.conferences.mapper.RequestToReportTopicWithSpeakerMapper;
import com.conferences.service.abstraction.IReportTopicService;
import com.conferences.service.implementation.ReportTopicService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class CreateCommand extends FrontCommand {

    private final IReportTopicService reportTopicService;
    private final IMapper<HttpServletRequest, ReportTopic> mapper;

    public CreateCommand() {
        reportTopicService = new ReportTopicService();
        mapper = new RequestToReportTopicWithSpeakerMapper();
    }

    @Override
    public void process() throws ServletException, IOException {
        if (!request.getMethod().equals(HttpMethod.POST.toString())) {
            return;
        }

        ReportTopic reportTopic = mapper.map(request);

        if (reportTopicService.saveWithSpeaker(reportTopic)) {
            redirect(Pages.MEETING.getUrl() + reportTopic.getMeetingId());
        } else {
            // TODO(Process saving error)
        }
    }
}
