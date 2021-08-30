package com.conferences.command.home;

import com.conferences.command.FrontCommand;
import com.conferences.config.Pages;

import java.io.IOException;

public class LogoutCommand extends FrontCommand {
    @Override
    public void process() throws IOException {
        request.getSession().invalidate();
        redirect(Pages.HOME.toString());
    }
}
