package com.conferences.controller;

import com.conferences.factory.HandlerFactory;
import com.conferences.handler.abstraction.ICommandHandler;
import com.conferences.command.FrontCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * <p>
 *     {@link HttpServlet} representing Front Controller pattern
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 1,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 100
)
public class FrontController extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(FrontController.class);

    private ICommandHandler commandHandler;

    @Override
    public void init() throws ServletException {
        commandHandler = HandlerFactory.getInstance().getCommandHandler();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * <p>
     *      Delegates request to command
     * </p>
     * @param request {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @throws IOException exception may occur during processing request
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LOGGER.info("{} request processing started", request.getMethod());

        try {
            FrontCommand command = commandHandler.getCommand(request, response);
            LOGGER.info("Processing request");
            command.process();
        } catch (NumberFormatException exception) {
            LOGGER.error("Unable to process request", exception);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception exception) {
            LOGGER.error("Unable to process request", exception);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
