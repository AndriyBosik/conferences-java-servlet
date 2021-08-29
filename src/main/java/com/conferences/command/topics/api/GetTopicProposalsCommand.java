package com.conferences.command.topics.api;

import com.conferences.command.FrontCommand;
import com.conferences.command.JsonApiCommand;
import com.conferences.entity.SpeakerProposal;
import com.conferences.service.abstraction.ISpeakerProposalService;
import com.conferences.service.implementation.SpeakerProposalService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class GetTopicProposalsCommand extends JsonApiCommand {

    private final int topicId;
    private final ISpeakerProposalService speakerProposalService;

    public GetTopicProposalsCommand(List<String> urlParams) {
        speakerProposalService = new SpeakerProposalService();

        topicId = Integer.parseInt(urlParams.get(0));
    }

    @Override
    public void process() throws ServletException, IOException {
        List<SpeakerProposal> proposals = speakerProposalService.getTopicProposals(topicId);

        Gson gson = new Gson();
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(proposals));
    }
}
