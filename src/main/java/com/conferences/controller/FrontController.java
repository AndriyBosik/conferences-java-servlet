package com.conferences.controller;

import com.conferences.command.CommandInfo;
import com.conferences.command.FrontCommand;
import com.conferences.command.UnknownCommand;
import com.conferences.config.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

        request.setAttribute("languages", Constants.LANGUAGES);
        request.setAttribute("currentLanguage", request.getSession().getAttribute("lang"));

        String path = request.getPathInfo().substring(contextPathLength);
        CommandInfo commandInfo = getCommandInfo(path);
        FrontCommand command = getCommand(commandInfo);

        command.init(getServletContext(), request, response);
        command.process();
    }

    private FrontCommand getCommand(CommandInfo commandInfo) {
        try {
            Class type = Class.forName(String.format("com.conferences.command.%s.%sCommand", commandInfo.getPackageName(), commandInfo.getCommandName()));
            if (commandInfo.getUrlParams().isEmpty()) {
                return (FrontCommand) type.asSubclass(FrontCommand.class).newInstance();
            } else {
                return (FrontCommand) type.asSubclass(FrontCommand.class).getConstructor(List.class).newInstance(commandInfo.getUrlParams());
            }
        } catch (Exception exception) {
            exception.printStackTrace();
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

        List<String> urlParams = new ArrayList<>();
        for (int i = 2; i < parts.length; i++) {
            urlParams.add(parts[i]);
        }
        return new CommandInfo(parts[0], parts[1], urlParams);
    }
}
