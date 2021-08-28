package com.conferences.command.topics;

import com.conferences.command.FrontCommand;
import com.conferences.dao.implementation.ReportTopicDao;
import com.conferences.entity.ReportTopic;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

public class ShowCommand extends FrontCommand {

    private int id;

    public ShowCommand(List<String> urlParams) {
        id = Integer.parseInt(urlParams.get(0));
    }

    @Override
    public void process() throws ServletException, IOException {
        request.setAttribute("id", id);

        forwardPartial("topic");
    }
}
