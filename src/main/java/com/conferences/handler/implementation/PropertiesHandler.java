package com.conferences.handler.implementation;

import com.conferences.handler.abstraction.IPropertiesHandler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesHandler implements IPropertiesHandler {

    @Override
    public String getPropertyValue(String filename, String lang, String key) {
        String propertiesFilename = filename + "_" + lang + ".properties";
        Properties properties = new Properties();
        try {
            properties.load(new InputStreamReader(PropertiesHandler.class.getClassLoader().getResourceAsStream(propertiesFilename), "UTF-8"));

            String value = properties.getProperty(key);
            if (value == null) {
                return key;
            }
            return value;
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return "";
    }

}
