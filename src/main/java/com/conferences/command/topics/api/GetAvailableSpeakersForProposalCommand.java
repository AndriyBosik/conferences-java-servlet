package com.conferences.command.topics.api;

import com.conferences.command.JsonApiCommand;
import com.conferences.factory.ServiceFactory;
import com.conferences.service.abstraction.IModeratorProposalService;
import com.conferences.service.implementation.ModeratorProposalService;

import java.util.List;

public class GetAvailableSpeakersForProposalCommand extends JsonApiCommand {

    private int topicId;
    private final IModeratorProposalService moderatorProposalService;

    public GetAvailableSpeakersForProposalCommand(List<String> urlParams) {
        topicId = Integer.parseInt(urlParams.get(0));
        moderatorProposalService = ServiceFactory.getInstance().getModeratorProposalService();
    }

    @Override
    protected Object getJsonObject() {
        return moderatorProposalService.getAvailableSpeakersForProposalByTopic(topicId);
    }
}
