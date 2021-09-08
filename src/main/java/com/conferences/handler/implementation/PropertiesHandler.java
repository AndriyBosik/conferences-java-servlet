package com.conferences.handler.implementation;

import com.conferences.handler.abstraction.IPropertiesHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesHandler implements IPropertiesHandler {

    private static final Logger LOGGER = LogManager.getLogger(PropertiesHandler.class);

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
            LOGGER.error("Unable to get {} value", key, exception);
        }
        return "";
    }

}
