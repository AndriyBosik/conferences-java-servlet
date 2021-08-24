package com.conferences.command.home;

import com.conferences.command.FrontCommand;
import com.conferences.model.User;
import com.conferences.service.abstraction.IUserService;
import com.conferences.service.implementation.UserService;

import javax.servlet.ServletException;
import java.io.IOException;

public class ProfileCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");

        request.setAttribute("user", user);
        forwardPartial("profile");
    }
}
