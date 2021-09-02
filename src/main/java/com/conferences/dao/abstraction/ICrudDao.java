package com.conferences.dao.abstraction;

import java.util.List;

public interface ICrudDao<K, T> extends IDao<K, T> {

    boolean create(T model);

    T find(K key);

    boolean update(T model);

    boolean delete(K key);

    List<T> findAll();

    int getRecordsCount();

}