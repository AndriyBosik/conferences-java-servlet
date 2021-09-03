package com.conferences.dao.abstraction;

import com.conferences.factory.HandlerFactory;
import com.conferences.factory.ReflectionFactory;
import com.conferences.handler.abstraction.ITransactionHandler;
import com.conferences.reflection.abstraction.IEntityParser;
import com.conferences.reflection.abstraction.IEntityProcessor;

public abstract class AbstractDao<K, T> implements IDao<K, T> {

    protected final IEntityParser entityParser;
    protected final IEntityProcessor entityProcessor;
    protected final ITransactionHandler transactionHandler;

    public AbstractDao() {
        entityParser = ReflectionFactory.getInstance().getEntityParser();
        entityProcessor = ReflectionFactory.getInstance().getEntityProcessor();
        transactionHandler = HandlerFactory.getInstance().getTransactionHandler();
    }

}
