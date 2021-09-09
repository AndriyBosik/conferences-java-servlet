package com.conferences.command.topics;

import com.conferences.command.FrontCommand;
import com.conferences.config.Defaults;
import com.conferences.config.ErrorKey;
import com.conferences.config.FormKeys;
import com.conferences.entity.ReportTopicSpeaker;
import com.conferences.entity.User;
import com.conferences.entity.ReportTopic;
import com.conferences.factory.ServiceFactory;
import com.conferences.model.FormError;
import com.conferences.service.abstraction.IReportTopicService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *     Responds to /topics/set-speaker-from-proposals page requests
 * </p>
 */
public class SetSpeakerFromProposalsCommand extends FrontCommand {

    private IReportTopicService reportTopicService;

    @Override
    public void init(ServletContext context, HttpServletRequest request, HttpServletResponse response, List<String> urlParams) throws IOException {
        super.init(context, request, response, urlParams);
        reportTopicService = ServiceFactory.getInstance().getReportTopicService();
    }

    /**
     * <p>
     *     Assigns speaker for {@link ReportTopic}
     * </p>
     * @throws ServletException an exception which may occur during saving errors to session
     * @throws IOException an exception which may occur during saving errors to session
     */
    @Override
    public void process() throws ServletException, IOException {
        int speakerId = getSpeakerFromRequest();
        if (speakerId <= 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        int topicId = Integer.parseInt(request.getParameter("topic_id"));

        ReportTopicSpeaker reportTopicSpeaker = new ReportTopicSpeaker();
        reportTopicSpeaker.setReportTopicId(topicId);
        reportTopicSpeaker.setSpeakerId(speakerId);
        if (!reportTopicService.setSpeakerForTopic(reportTopicSpeaker)) {
            List<FormError> errors = new ArrayList<>();
            errors.add(new FormError(ErrorKey.UPDATING_ERROR));
            saveErrorsToSession(FormKeys.PROPOSAL_ERRORS, errors);
        }
        redirectBack();
    }

    private int getSpeakerFromRequest() {
        String strSpeakerId = request.getParameter("speaker_id");
        if (strSpeakerId == null) {
            User user = (User) request.getSession().getAttribute(Defaults.USER.toString());
            return user.getId();
        }
        return Integer.parseInt(strSpeakerId);
    }
}
