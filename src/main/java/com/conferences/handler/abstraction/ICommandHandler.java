package com.conferences.handler.abstraction;

import com.conferences.command.FrontCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 *     Defines method to parse command
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public interface ICommandHandler {

    /**
     * <p>
     *     Instantiates command from request
     * </p>
     * @param request {@link HttpServletRequest} representing HTTP request
     * @param response {@link HttpServletResponse} representing HTTP response
     * @return an instance of {@link FrontCommand}
     * @throws IOException exception may occur during command initialization
     */
    FrontCommand getCommand(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
