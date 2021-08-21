package com.conferences.command.role;

import com.conferences.command.FrontCommand;

import javax.servlet.ServletException;
import java.io.IOException;

public class IndexCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        System.out.println("Role.Index method");
    }
}
