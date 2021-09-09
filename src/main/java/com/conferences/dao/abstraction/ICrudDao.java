package com.conferences.dao.abstraction;

import java.util.List;

/**
 * <p>
 *     Defines basic CRUD methods
 * </p>
 * @param <K> primary key type
 * @param <T> entity type
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public interface ICrudDao<K, T> extends IDao<K, T> {

    /**
     * <p>
     *     Creates entity in database
     * </p>
     * @param entity entity to be created in database
     * @return true if entity was successfully created, false otherwise
     */
    boolean create(T entity);

    /**
     * <p>
     *     Retrieves entity from database by {@code key}
     * </p>
     * @param key key to find entity by
     * @return entity or null if no entity was found
     */
    T find(K key);

    /**
     * <p>
     *     Updates entity data
     * </p>
     * @param entity entity which contains updated data
     * @return true if entity was successfully updated, false otherwise
     */
    boolean update(T entity);

    /**
     * <p>
     *     Deletes entity from database
     * </p>
     * @param key key to delete entity by
     * @return true if entity was successfully deleted, false otherwise
     */
    boolean delete(K key);

    /**
     * <p>
     *     Retrieves all entities from database
     * </p>
     * @return list of entities of T type
     */
    List<T> findAll();

    /**
     * <p>
     *     Retrieves records count
     * </p>
     * @return number representing count of records present in table
     */
    int getRecordsCount();
}
