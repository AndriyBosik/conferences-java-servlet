package com.conferences.handler.implementation;

import com.conferences.handler.abstraction.IJsonHandler;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonHandler implements IJsonHandler {

    private Gson gson;

    public JsonHandler() {
        gson = new Gson();
    }

    @Override
    public <T> T parseJsonRequestBodyToObject(HttpServletRequest request, Class<T> objectClass) {
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = null;
        try (InputStream inputStream = request.getInputStream()) {
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    sb.append(charBuffer, 0, bytesRead);
                }

                return gson.fromJson(sb.toString(), objectClass);
            } else {
                return null;
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        }
        return null;
    }
}
