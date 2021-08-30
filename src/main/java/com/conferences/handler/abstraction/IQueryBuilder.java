package com.conferences.handler.abstraction;

public interface IQueryBuilder {

    IQueryBuilder select(String... columns);

    IQueryBuilder from(String... tables);

    IQueryBuilder where(String condition);

    String and(String... conditions);

    IQueryBuilder leftJoin(String tableName, String condition);

    IQueryBuilder groupBy(String... columns);

    IQueryBuilder orderBy(String... columns);

    String generateQuery();

}
