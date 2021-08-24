package com.conferences.command.home;

import com.conferences.command.FrontCommand;

import javax.servlet.ServletException;
import java.io.IOException;

public class ProfileCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        forwardPartial("profile");
    }
}
