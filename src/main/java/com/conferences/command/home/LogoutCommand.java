package com.conferences.command.home;

import com.conferences.command.FrontCommand;
import com.conferences.config.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * <p>
 *     Responds to /home/logout request
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public class LogoutCommand extends FrontCommand {

    private static final Logger LOGGER = LogManager.getLogger(LogoutCommand.class);

    /**
     * <p>
     *     Makes user logging out
     * </p>
     * @throws IOException an exception which may occur during saving errors to session
     */
    @Override
    public void process() throws IOException {
        LOGGER.info("Invalidation session");
        request.getSession().invalidate();
        redirect(Page.HOME.toString());
    }
}
