package com.conferences.handler.abstraction;

import com.conferences.model.CommandInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *      Defines method to extract {@link CommandInfo} from HTTP request
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public interface ICommandInfoHandler {

    /**
     * <p>
     *     Extracts {@link CommandInfo} from {@link HttpServletRequest} object
     * </p>
     * @param request instance of {@link HttpServletRequest} class representing HTTP request
     * @return {@link CommandInfo}
     */
    CommandInfo getCommandInfoFromRequest(HttpServletRequest request);
}
