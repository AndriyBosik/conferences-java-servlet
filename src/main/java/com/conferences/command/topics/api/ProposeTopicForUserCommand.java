package com.conferences.command.topics.api;

import com.conferences.command.JsonApiCommand;
import com.conferences.entity.ModeratorProposal;
import com.conferences.factory.ServiceFactory;
import com.conferences.model.SimpleResponse;
import com.conferences.service.abstraction.IModeratorProposalService;
import com.conferences.service.implementation.ModeratorProposalService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ProposeTopicForUserCommand extends JsonApiCommand {

    private final IModeratorProposalService moderatorProposalService;

    public ProposeTopicForUserCommand() {
        moderatorProposalService = ServiceFactory.getInstance().getModeratorProposalService();
    }

    @Override
    protected Object getJsonObject() {
        ModeratorProposal proposal = jsonHandler.parseJsonRequestBodyToObject(request, ModeratorProposal.class);

        if (moderatorProposalService.saveModeratorProposal(proposal)) {
            return new SimpleResponse("success", "");
        } else {
            return new SimpleResponse("error", "Something went wrong...");
        }
    }
}
