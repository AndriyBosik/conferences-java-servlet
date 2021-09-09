package com.conferences.factory;

import com.conferences.handler.abstraction.*;
import com.conferences.handler.implementation.*;

/**
 * <p>
 *     Defines methods to instantiate handlers
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public class HandlerFactory {

    private static HandlerFactory instance;

    private ICommandHandler commandHandler;
    private ICommandInfoHandler commandInfoHandler;
    private IEncryptor encryptor;
    private IFieldValidationHandler fieldValidationHandler;
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

    public synchronized ICommandHandler getCommandHandler() {
        if (commandHandler == null) {
            commandHandler = new CommandHandler();
        }
        return commandHandler;
    }

    public synchronized ICommandInfoHandler getCommandInfoHandler() {
        if (commandInfoHandler == null) {
            commandInfoHandler = new CommandInfoHandler();
        }
        return commandInfoHandler;
    }

    public synchronized IEncryptor getEncryptor() {
        if (encryptor == null) {
            encryptor = new Encryptor();
        }
        return encryptor;
    }

    public synchronized IFieldValidationHandler getFieldValidationHandler() {
        if (fieldValidationHandler == null) {
            fieldValidationHandler = new FieldValidationHandler();
        }
        return fieldValidationHandler;
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
