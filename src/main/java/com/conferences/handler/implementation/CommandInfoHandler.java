package com.conferences.handler.implementation;

import com.conferences.handler.abstraction.ICommandInfoHandler;
import com.conferences.model.CommandInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * {@inheritDoc}
 */
public class CommandInfoHandler implements ICommandInfoHandler {

    private static final Logger LOGGER = LogManager.getLogger(CommandInfoHandler.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public CommandInfo getCommandInfoFromRequest(HttpServletRequest request) {
        int contextPathLength = request.getContextPath().length();
        if (contextPathLength != request.getRequestURI().length()) {
            contextPathLength++;
        }
        String path = request.getPathInfo().substring(contextPathLength);

        CommandInfo commandInfo = getCommandInfoFromUrlPath(path);
        LOGGER.info("Request path: {}; Command: {}.{}; urlParams: {}", path, commandInfo.getPackageName(), commandInfo.getCommandName(), commandInfo.getUrlParams());
        return commandInfo;
    }

    /**
     * <p>
     *     Instantiates {@link CommandInfo} object based on URL
     * </p>
     * @param path string representing URL
     * @return {@link CommandInfo} object
     */
    private CommandInfo getCommandInfoFromUrlPath(String path) {
        if (path.length() == 0) {
            return new CommandInfo("home", "index");
        }
        String[] parts = path.split("/");
        if (parts.length == 1) {
            return new CommandInfo("home", parts[0]);
        }

        String[] urlParamsArray = new String[parts.length - 2];
        System.arraycopy(parts, 2, urlParamsArray, 0, parts.length - 2);
        List<String> urlParams = Arrays.asList(urlParamsArray);
        return new CommandInfo(parts[0].replace("-", "."), parts[1], urlParams);
    }
}
