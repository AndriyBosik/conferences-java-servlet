package com.conferences.command.topics.api;

import com.conferences.command.JsonApiCommand;
import com.conferences.service.abstraction.ISpeakerProposalService;
import com.conferences.service.implementation.SpeakerProposalService;

import java.util.List;

public class GetTopicProposalsCommand extends JsonApiCommand {

    private final int topicId;
    private final ISpeakerProposalService speakerProposalService;

    public GetTopicProposalsCommand(List<String> urlParams) {
        speakerProposalService = new SpeakerProposalService();

        topicId = Integer.parseInt(urlParams.get(0));
    }

    @Override
    protected Object getJsonObject() {
        return speakerProposalService.getTopicProposals(topicId);
    }
}
