package com.conferences.factory;

import com.conferences.reflection.abstraction.IEntityParser;
import com.conferences.reflection.abstraction.IEntityProcessor;
import com.conferences.reflection.implementation.EntityParser;
import com.conferences.reflection.implementation.EntityProcessor;

/**
 * <p>
 *     Defines methods to instantiate reflection utils
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public class ReflectionFactory {

    private static ReflectionFactory instance;

    private IEntityParser entityParser;
    private IEntityProcessor entityProcessor;

    private ReflectionFactory() {}

    public static synchronized ReflectionFactory getInstance() {
        if (instance == null) {
            instance = new ReflectionFactory();
        }
        return instance;
    }

    public synchronized IEntityParser getEntityParser() {
        if (entityParser == null) {
            entityParser = new EntityParser();
        }
        return entityParser;
    }

    public synchronized IEntityProcessor getEntityProcessor() {
        if (entityProcessor == null) {
            entityProcessor = new EntityProcessor();
        }
        return entityProcessor;
    }
}
