package com.conferences.command.topics;

import com.conferences.command.FrontCommand;
import com.conferences.service.abstraction.IProposedTopicDataService;
import com.conferences.service.implementation.ProposedTopicDataService;

import javax.servlet.ServletException;
import java.io.IOException;

public class SpeakerProposedCommand extends FrontCommand {

    private final IProposedTopicDataService proposedTopicDataService;

    public SpeakerProposedCommand() {
        proposedTopicDataService = new ProposedTopicDataService();
    }

    @Override
    public void process() throws ServletException, IOException {
        request.setAttribute("proposedTopics", proposedTopicDataService.getProposedTopicsOrderByMeeting());

        forwardPartial("proposed_topics");
    }
}
