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
import java.util.List;

/**
 * <p>
 *     Represents command which can receive and send JSON objects
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public abstract class JsonApiCommand extends FrontCommand {

    protected Gson gson;
    protected IJsonHandler jsonHandler;
    protected IMapper<FormError, String> errorMapper;

    @Override
    public void init(ServletContext context, HttpServletRequest request, HttpServletResponse response, List<String> urlParams) throws IOException {
        super.init(context, request, response, urlParams);
        gson = new Gson();
        jsonHandler = HandlerFactory.getInstance().getJsonHandler();
        errorMapper = MapperFactory.getInstance().getFormErrorToStringMapper();
        response.setContentType("application/json");
    }

    /**
     * <p>
     *     Receives object from abstract {@code getJsonObject()} method and maps it to JSON string
     * </p>
     * @throws ServletException an exception which may occur during saving errors to session
     * @throws IOException an exception which may occur during saving errors to session
     */
    @Override
    public void process() throws ServletException, IOException {
        Object model = getJsonObject();
        PrintWriter out = response.getWriter();
        out.write(gson.toJson(model));
    }

    /**
     * <p>
     *     Returns object which will be mapped to JSON string
     * </p>
     * @return object of any class
     */
    protected abstract Object getJsonObject();
}
