package com.conferences.command;

import com.conferences.factory.HandlerFactory;
import com.conferences.factory.MapperFactory;
import com.conferences.handler.abstraction.IJsonHandler;
import com.conferences.mapper.IMapper;
import com.conferences.model.FormError;
import com.google.gson.Gson;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class JsonApiCommand extends FrontCommand {

    protected Gson gson;
    protected IJsonHandler jsonHandler;
    protected IMapper<FormError, String> errorMapper;

    @Override
    public void init(ServletContext context, HttpServletRequest request, HttpServletResponse response) {
        super.init(context, request, response);
        gson = new Gson();
        jsonHandler = HandlerFactory.getInstance().getJsonHandler();
        errorMapper = MapperFactory.getInstance().getFormErrorToStringMapper();
        response.setContentType("application/json");
    }

    @Override
    public void process() throws ServletException, IOException {
        Object model = getJsonObject();
        PrintWriter out = response.getWriter();
        out.write(gson.toJson(model));
    }

    protected abstract Object getJsonObject();
}
