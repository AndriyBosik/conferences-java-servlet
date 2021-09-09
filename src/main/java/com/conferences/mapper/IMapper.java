package com.conferences.mapper;

/**
 * <p>
 *     Defines a function that map object of T class to object of U class
 * </p>
 * @param <T> the type of object to be mapped
 * @param <U> the type of object to map to
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public interface IMapper<T, U> {

    /**
     * <p>
     *     Maps object of T class to object of U class
     * </p>
     * @param model object to be mapped to
     * @return result of mapping
     */
    U map(T model);
}
