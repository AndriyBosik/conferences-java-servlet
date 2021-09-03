package com.conferences.command.topics;

import com.conferences.command.FrontCommand;
import com.conferences.config.Defaults;
import com.conferences.entity.ReportTopicSpeaker;
import com.conferences.entity.User;
import com.conferences.factory.ServiceFactory;
import com.conferences.service.abstraction.IReportTopicService;

import javax.servlet.ServletException;
import java.io.IOException;

public class SetSpeakerFromProposalsCommand extends FrontCommand {

    private final IReportTopicService reportTopicService;

    public SetSpeakerFromProposalsCommand() {
        reportTopicService = ServiceFactory.getInstance().getReportTopicService();
    }

    @Override
    public void process() throws ServletException, IOException {
        int speakerId = Integer.parseInt(request.getParameter("speaker_id"));
        int topicId = Integer.parseInt(request.getParameter("topic_id"));

        if (!validSpeaker(speakerId)) {
            // TODO(speakers try to assign propose to another speaker)
            return;
        }

        ReportTopicSpeaker reportTopicSpeaker = new ReportTopicSpeaker();
        reportTopicSpeaker.setReportTopicId(topicId);
        reportTopicSpeaker.setSpeakerId(speakerId);
        if (reportTopicService.setSpeakerForTopic(reportTopicSpeaker)) {
            redirectBack();
        } else {
            // TODO(another speaker already accept moderator proposal)
        }
    }

    private boolean validSpeaker(int speakerId) {
        User user = (User) request.getSession().getAttribute(Defaults.USER.toString());
        return "moderator".equals(user.getRole().getTitle()) || user.getId() == speakerId;
    }
}
