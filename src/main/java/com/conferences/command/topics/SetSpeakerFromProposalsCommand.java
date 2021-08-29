package com.conferences.command.topics;

import com.conferences.command.FrontCommand;
import com.conferences.config.Pages;
import com.conferences.entity.ReportTopicSpeaker;
import com.conferences.service.abstraction.IReportTopicService;
import com.conferences.service.implementation.ReportTopicService;

import javax.servlet.ServletException;
import java.io.IOException;

public class SetSpeakerFromProposalsCommand extends FrontCommand {

    private final IReportTopicService reportTopicService;

    public SetSpeakerFromProposalsCommand() {
        reportTopicService = new ReportTopicService();
    }

    @Override
    public void process() throws ServletException, IOException {
        int speakerId = Integer.parseInt(request.getParameter("speaker_id"));
        int topicId = Integer.parseInt(request.getParameter("topic_id"));
        int meetingId = Integer.parseInt(request.getParameter("meeting_id"));

        ReportTopicSpeaker reportTopicSpeaker = new ReportTopicSpeaker();
        reportTopicSpeaker.setReportTopicId(topicId);
        reportTopicSpeaker.setSpeakerId(speakerId);
        if (reportTopicService.setSpeakerForTopic(reportTopicSpeaker)) {
            redirect(Pages.MEETING.getUrl() + meetingId);
        } else {
            // TODO
        }
    }
}
