package com.conferences.controller;

import com.conferences.command.CommandInfo;
import com.conferences.command.FrontCommand;
import com.conferences.command.UnknownCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class FrontController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int contextPathLength = request.getContextPath().length();
        if (contextPathLength != request.getRequestURI().length()) {
            contextPathLength++;
        }
        String path = request.getPathInfo().substring(contextPathLength);
        CommandInfo commandInfo = getCommandInfo(path);
        FrontCommand command = getCommand(commandInfo);

        command.init(getServletContext(), request, response);
        command.process();
    }

    private FrontCommand getCommand(CommandInfo commandInfo) {
        try {
            Class type = Class.forName(String.format("com.conferences.command.%s.%sCommand", commandInfo.getPackageName(), commandInfo.getCommandName()));
            return (FrontCommand) type.asSubclass(FrontCommand.class).newInstance();
        } catch (Exception exception) {
            return new UnknownCommand();
        }
    }

    private CommandInfo getCommandInfo(String path) {
        if (path.length() == 0) {
            return new CommandInfo("home", "index");
        }
        String[] parts = path.split("/");
        if (parts.length == 1) {
            return new CommandInfo("home", parts[0]);
        }
        return new CommandInfo(parts[0], parts[1]);
    }
}
