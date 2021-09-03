package com.conferences.command.topics;

import com.conferences.command.FrontCommand;
import com.conferences.config.HttpMethod;
import com.conferences.config.Page;
import com.conferences.entity.ReportTopic;
import com.conferences.factory.MapperFactory;
import com.conferences.factory.ServiceFactory;
import com.conferences.mapper.IMapper;
import com.conferences.service.abstraction.IReportTopicService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class UpdateCommand extends FrontCommand {

    private final IReportTopicService reportTopicService;
    private final IMapper<HttpServletRequest, ReportTopic> mapper;

    public UpdateCommand() {
        reportTopicService = ServiceFactory.getInstance().getReportTopicService();
        mapper = MapperFactory.getInstance().getRequestToReportTopicWithSpeakerMapper();
    }

    @Override
    public void process() throws ServletException, IOException {
        if (!request.getMethod().equals(HttpMethod.POST.toString())) {
            return;
        }

        ReportTopic reportTopic = mapper.map(request);

        if (reportTopicService.updateTopicWithSpeaker(reportTopic)) {
            redirect(Page.MEETING.getUrl() + reportTopic.getMeetingId());
        } else {
            // Process request error
        }
    }
}
