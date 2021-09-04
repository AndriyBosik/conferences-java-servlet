package com.conferences.command.topics.api;

import com.conferences.command.JsonApiCommand;
import com.conferences.factory.ServiceFactory;
import com.conferences.service.abstraction.IModeratorProposalService;
import com.conferences.service.implementation.ModeratorProposalService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GetAvailableSpeakersForProposalCommand extends JsonApiCommand {

    private int topicId;
    private IModeratorProposalService moderatorProposalService;

    @Override
    public void init(ServletContext context, HttpServletRequest request, HttpServletResponse response, List<String> urlParams) {
        super.init(context, request, response, urlParams);
        topicId = Integer.parseInt(urlParams.get(0));
        moderatorProposalService = ServiceFactory.getInstance().getModeratorProposalService();
    }

    @Override
    protected Object getJsonObject() {
        return moderatorProposalService.getAvailableSpeakersForProposalByTopic(topicId);
    }
}
