package com.conferences.handler.abstraction;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface IFileHandler {

    Map<String, String> saveFile(HttpServletRequest request, String path);

}
