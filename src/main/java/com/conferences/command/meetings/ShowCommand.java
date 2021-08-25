package com.conferences.command.meetings;

import com.conferences.command.FrontCommand;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

public class ShowCommand extends FrontCommand {

    private int id;
    private String title;

    public ShowCommand(List<String> urlParams) {
        id = Integer.parseInt(urlParams.get(0));
        title = urlParams.get(1);
    }

    @Override
    public void process() throws ServletException, IOException {
        request.setAttribute("id", id);
        request.setAttribute("title", title);

        forwardPartial("meeting_topics");
    }
}
