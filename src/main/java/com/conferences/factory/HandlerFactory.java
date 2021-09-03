package com.conferences.factory;

import com.conferences.handler.abstraction.*;
import com.conferences.handler.implementation.*;

public class HandlerFactory {

    private static HandlerFactory instance;

    private IFileHandler fileHandler;
    private IJsonHandler jsonHandler;
    private ILinkHandler linkHandler;
    private IPropertiesHandler propertiesHandler;
    private ITransactionHandler transactionHandler;
    private IUserDataSaver userDataSaver;

    private HandlerFactory() {}

    public static synchronized HandlerFactory getInstance() {
        if (instance == null) {
            instance = new HandlerFactory();
        }
        return instance;
    }

    public synchronized IFileHandler getFileHandler() {
        if (fileHandler == null) {
            fileHandler = new FileHandler();
        }
        return fileHandler;
    }

    public synchronized IJsonHandler getJsonHandler() {
        if (jsonHandler == null) {
            jsonHandler = new JsonHandler();
        }
        return jsonHandler;
    }

    public synchronized ILinkHandler getLinkHandler() {
        if (linkHandler == null) {
            linkHandler = new LinkHandler();
        }
        return linkHandler;
    }

    public synchronized IPropertiesHandler getPropertiesHandler() {
        if (propertiesHandler == null) {
            propertiesHandler = new PropertiesHandler();
        }
        return propertiesHandler;
    }

    public synchronized ITransactionHandler getTransactionHandler() {
        if (transactionHandler == null) {
            transactionHandler = new TransactionHandler();
        }
        return transactionHandler;
    }

    public synchronized IUserDataSaver getUserDataSaver() {
        if (userDataSaver == null) {
            userDataSaver = new UserDataSaver();
        }
        return userDataSaver;
    }
}
