package com.conferences.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 *     Represents command which is instantiated when no other suitable command was found
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public class UnknownCommand extends FrontCommand {

    @Override
    public void process() throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

}
