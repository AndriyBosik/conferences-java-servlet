package com.conferences.dao.abstraction;

import com.conferences.handler.TransactionHandler;
import com.conferences.reflection.abstraction.IEntityParser;
import com.conferences.reflection.abstraction.IEntityProcessor;
import com.conferences.reflection.implementation.EntityParser;
import com.conferences.reflection.implementation.EntityProcessor;

public class AbstractDao<K, T> implements IDao<K, T> {

    protected final IEntityParser entityParser;
    protected final IEntityProcessor entityProcessor;
    protected final TransactionHandler transactionHandler;

    public AbstractDao() {
        entityParser = new EntityParser();
        entityProcessor = new EntityProcessor();
        transactionHandler = new TransactionHandler();
    }

}
