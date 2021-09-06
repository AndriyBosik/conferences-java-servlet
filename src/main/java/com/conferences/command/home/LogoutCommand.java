package com.conferences.command.home;

import com.conferences.command.FrontCommand;
import com.conferences.config.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class LogoutCommand extends FrontCommand {

    private static final Logger LOGGER = LogManager.getLogger(LogoutCommand.class);

    @Override
    public void process() throws IOException {
        LOGGER.info("Invalidation session");
        request.getSession().invalidate();
        redirect(Page.HOME.toString());
    }
}
