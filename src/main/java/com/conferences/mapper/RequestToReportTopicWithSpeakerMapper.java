package com.conferences.mapper;

import com.conferences.entity.ReportTopic;
import com.conferences.entity.ReportTopicSpeaker;
import com.conferences.handler.abstraction.IEncodingHandler;
import com.conferences.handler.implementation.EncodingHandler;

import javax.servlet.http.HttpServletRequest;

public class RequestToReportTopicWithSpeakerMapper implements IMapper<HttpServletRequest, ReportTopic> {

    private final IEncodingHandler encodingHandler;

    public RequestToReportTopicWithSpeakerMapper() {
        encodingHandler = new EncodingHandler();
    }

    @Override
    public ReportTopic map(HttpServletRequest request) {
        ReportTopic reportTopic = new ReportTopic();

        String idParameter = request.getParameter("id");
        if (idParameter != null && !idParameter.trim().isEmpty()) {
            reportTopic.setId(Integer.parseInt(idParameter));
        }

        reportTopic.setTitle(encodingHandler.getUTF8ValueFromRequest(request, "title"));

        reportTopic.setMeetingId(Integer.parseInt(request.getParameter("meeting_id")));

        String speakerIdParameter = request.getParameter("speaker_id");
        ReportTopicSpeaker reportTopicSpeaker = new ReportTopicSpeaker();
        if (speakerIdParameter != null && !speakerIdParameter.trim().isEmpty()) {
            reportTopicSpeaker.setSpeakerId(Integer.parseInt(request.getParameter("speaker_id")));
            reportTopic.setReportTopicSpeaker(reportTopicSpeaker);

            String reportTopicSpeakerIdParameter = request.getParameter("report_topic_speaker_id");
            if (reportTopicSpeakerIdParameter != null && !reportTopicSpeakerIdParameter.trim().isEmpty()) {
                reportTopic.getReportTopicSpeaker().setId(Integer.parseInt(reportTopicSpeakerIdParameter.trim()));
            }
        }
        return reportTopic;
    }
}
