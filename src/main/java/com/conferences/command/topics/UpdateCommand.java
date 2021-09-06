package com.conferences.command.topics;

import com.conferences.command.FrontCommand;
import com.conferences.config.FormKeys;
import com.conferences.config.Page;
import com.conferences.entity.ReportTopic;
import com.conferences.factory.MapperFactory;
import com.conferences.factory.ServiceFactory;
import com.conferences.mapper.IMapper;
import com.conferences.model.FormError;
import com.conferences.service.abstraction.IReportTopicService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UpdateCommand extends FrontCommand {

    private IReportTopicService reportTopicService;
    private IMapper<HttpServletRequest, ReportTopic> mapper;

    @Override
    public void init(ServletContext context, HttpServletRequest request, HttpServletResponse response, List<String> urlParams) throws IOException {
        super.init(context, request, response, urlParams);
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
