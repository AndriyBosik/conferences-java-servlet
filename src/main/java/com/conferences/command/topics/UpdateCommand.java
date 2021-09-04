package com.conferences.command.topics;

import com.conferences.command.FrontCommand;
import com.conferences.config.FormKeys;
import com.conferences.config.HttpMethod;
import com.conferences.config.Page;
import com.conferences.entity.ReportTopic;
import com.conferences.factory.MapperFactory;
import com.conferences.factory.ServiceFactory;
import com.conferences.mapper.IMapper;
import com.conferences.model.FormError;
import com.conferences.service.abstraction.IReportTopicService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public class UpdateCommand extends FrontCommand {

    private final IReportTopicService reportTopicService;
    private final IMapper<HttpServletRequest, ReportTopic> mapper;

    public UpdateCommand() {
        reportTopicService = ServiceFactory.getInstance().getReportTopicService();
        mapper = MapperFactory.getInstance().getRequestToReportTopicWithSpeakerMapper();
    }

    @Override
    public void process() throws ServletException, IOException {
        ReportTopic reportTopic = mapper.map(request);

        List<FormError> errors = reportTopicService.updateTopicWithSpeaker(reportTopic);
        if (!errors.isEmpty()) {
            saveErrorsToSession(FormKeys.UPDATED_TOPIC_ERRORS, errors);
        }
        redirect(Page.MEETING.getUrl() + reportTopic.getMeetingId());
    }
}
