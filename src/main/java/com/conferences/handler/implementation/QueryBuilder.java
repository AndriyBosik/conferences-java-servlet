package com.conferences.handler.implementation;

import com.conferences.handler.abstraction.IQueryBuilder;

import java.util.Arrays;
import java.util.stream.Collectors;

public class QueryBuilder implements IQueryBuilder {

    private static final String AND_WHERE_SEPARATOR = " AND ";

    protected StringBuilder query;

    @Override
    public IQueryBuilder select(String... columns) {
        query = new StringBuilder("");
        query.append("SELECT ");
        for (String column: columns) {
            query.append(column).append(",");
        }
        query.deleteCharAt(query.length() - 1);
        return this;
    }

    @Override
    public IQueryBuilder from(String... tables) {
        query.append(" FROM ");
        for (String table: tables) {
            query.append(table).append(",");
        }
        query.deleteCharAt(query.length() - 1);
        return this;
    }

    @Override
    public IQueryBuilder where(String condition) {
        if (condition != null && condition.length() > 0) {
            query
                .append(" WHERE ")
                .append(condition);
        }
        return this;
    }

    @Override
    public String and(String... conditions) {
        conditions = Arrays.stream(conditions)
                .filter(condition -> condition != null && !condition.trim().isEmpty())
                .toArray(String[]::new);
        StringBuilder totalCondition = new StringBuilder();
        if (conditions.length > 0) {
            for (String condition: conditions) {
                totalCondition
                        .append("(")
                        .append(condition)
                        .append(")")
                        .append(AND_WHERE_SEPARATOR);
            }
            totalCondition.delete(totalCondition.length() - AND_WHERE_SEPARATOR.length(), totalCondition.length());
        }
        return totalCondition.toString();
    }

    @Override
    public IQueryBuilder leftJoin(String tableName, String condition) {
        if (tableName == null || tableName.trim().isEmpty()  || condition == null || condition.trim().isEmpty()) {
            return this;
        }
        query
            .append(" LEFT JOIN ")
            .append(tableName)
            .append(" ON ")
            .append(condition);
        return this;
    }

    @Override
    public IQueryBuilder groupBy(String... columns) {
        if (columns.length == 0) {
            return this;
        }
        query.append(" GROUP BY ");
        for (String column: columns) {
            query.append(column).append(",");
        }
        query.deleteCharAt(query.length() - 1);
        return this;
    }

    @Override
    public IQueryBuilder orderBy(String... columns) {
        if (columns.length == 0) {
            return this;
        }
        query.append(" ORDER BY ");
        for (String column: columns) {
            query.append(column).append(",");
        }
        query.deleteCharAt(query.length() - 1);
        return this;
    }

    @Override
    public String generateQuery() {
        return query.toString();
    }
}
