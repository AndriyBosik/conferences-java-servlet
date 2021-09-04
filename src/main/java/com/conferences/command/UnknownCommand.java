package com.conferences.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UnknownCommand extends FrontCommand {

    @Override
    public void process() throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

}
