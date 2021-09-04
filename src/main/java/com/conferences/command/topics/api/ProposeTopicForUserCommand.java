package com.conferences.command.topics.api;

import com.conferences.command.JsonApiCommand;
import com.conferences.config.Defaults;
import com.conferences.config.ErrorKey;
import com.conferences.entity.ModeratorProposal;
import com.conferences.factory.ServiceFactory;
import com.conferences.model.FormError;
import com.conferences.model.SimpleResponse;
import com.conferences.service.abstraction.IModeratorProposalService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ProposeTopicForUserCommand extends JsonApiCommand {

    private IModeratorProposalService moderatorProposalService;

    @Override
    public void init(ServletContext context, HttpServletRequest request, HttpServletResponse response, List<String> urlParams) {
        super.init(context, request, response, urlParams);
        moderatorProposalService = ServiceFactory.getInstance().getModeratorProposalService();
    }

    @Override
    protected Object getJsonObject() {
        ModeratorProposal proposal = jsonHandler.parseJsonRequestBodyToObject(request, ModeratorProposal.class);

        if (moderatorProposalService.saveModeratorProposal(proposal)) {
            return new SimpleResponse("success", "");
        } else {
            FormError error = new FormError((String) request.getAttribute(Defaults.CURRENT_LANG.toString()), ErrorKey.SAVING_PROPOSAL_ERROR);
            return new SimpleResponse("error", errorMapper.map(error));
        }
    }
}
