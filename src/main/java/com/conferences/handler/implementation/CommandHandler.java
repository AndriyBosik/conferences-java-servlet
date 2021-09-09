package com.conferences.handler.implementation;

import com.conferences.command.FrontCommand;
import com.conferences.command.UnknownCommand;
import com.conferences.factory.HandlerFactory;
import com.conferences.factory.MapperFactory;
import com.conferences.handler.abstraction.ICommandHandler;
import com.conferences.handler.abstraction.ICommandInfoHandler;
import com.conferences.mapper.IMapper;
import com.conferences.model.CommandInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * {@inheritDoc}
 */
public class CommandHandler implements ICommandHandler {

    private static final Logger LOGGER = LogManager.getLogger(CommandHandler.class);

    private final IMapper<String, String> commandMapper;
    private final ICommandInfoHandler commandInfoHandler;

    public CommandHandler() {
        commandMapper = MapperFactory.getInstance().getUrlToJavaCommandNameMapper();
        commandInfoHandler = HandlerFactory.getInstance().getCommandInfoHandler();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FrontCommand getCommand(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CommandInfo commandInfo = commandInfoHandler.getCommandInfoFromRequest(request);
        FrontCommand command;
        try {
            Class<?> type = Class.forName(String.format("com.conferences.command.%s.%sCommand", commandInfo.getPackageName(), commandMapper.map(commandInfo.getCommandName())));
            command = type.asSubclass(FrontCommand.class).newInstance();
        } catch (Exception exception) {
            LOGGER.error("Unable to get command from request", exception);
            command = new UnknownCommand();
        }
        command.init(request.getServletContext(), request, response, commandInfo.getUrlParams());
        return command;
    }
}
