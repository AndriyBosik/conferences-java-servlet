package com.conferences.command.topics.api;

import com.conferences.command.JsonApiCommand;
import com.conferences.factory.ServiceFactory;
import com.conferences.service.abstraction.ISpeakerProposalService;
import com.conferences.service.implementation.SpeakerProposalService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetTopicProposalsCommand extends JsonApiCommand {

    private int topicId;
    private ISpeakerProposalService speakerProposalService;

    @Override
    public void init(ServletContext context, HttpServletRequest request, HttpServletResponse response, List<String> urlParams) throws IOException {
        super.init(context, request, response, urlParams);
        speakerProposalService = ServiceFactory.getInstance().getSpeakerProposalService();
        topicId = Integer.parseInt(urlParams.get(0));
    }

    @Override
    protected Object getJsonObject() {
        return speakerProposalService.getTopicProposals(topicId);
    }
}
