package com.conferences.command.topics.api;

import com.conferences.command.JsonApiCommand;
import com.conferences.factory.ServiceFactory;
import com.conferences.service.abstraction.IModeratorProposalService;
import com.conferences.service.implementation.ModeratorProposalService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *     Responds to /topics-api/get-available-speakers-for-proposal page requests
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public class GetAvailableSpeakersForProposalCommand extends JsonApiCommand {

    private int topicId;
    private IModeratorProposalService moderatorProposalService;

    @Override
    public void init(ServletContext context, HttpServletRequest request, HttpServletResponse response, List<String> urlParams) throws IOException {
        super.init(context, request, response, urlParams);
        topicId = Integer.parseInt(urlParams.get(0));
        moderatorProposalService = ServiceFactory.getInstance().getModeratorProposalService();
    }

    /**
     * @return list of available speaker to propose topic for
     */
    @Override
    protected Object getJsonObject() {
        return moderatorProposalService.getAvailableSpeakersForProposalByTopic(topicId);
    }
}
