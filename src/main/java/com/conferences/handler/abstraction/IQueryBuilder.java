package com.conferences.handler.abstraction;

/**
 * <p>
 *     Defines methods to generate SQL query
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public interface IQueryBuilder {

    /**
     * <p>
     *     Adds columns to SQL query which should be selected from database
     * </p>
     * @param columns array of columns
     * @return {@link IQueryBuilder}
     */
    IQueryBuilder select(String... columns);

    /**
     * <p>
     *     Adds tables to SQL query to select data from
     * </p>
     * @param tables array of tables
     * @return {@link IQueryBuilder}
     */
    IQueryBuilder from(String... tables);

    /**
     * <p>
     *     Adds where condition to SQL query
     * </p>
     * @param condition string condition to add to WHERE clause
     * @return {@link IQueryBuilder}
     */
    IQueryBuilder where(String condition);

    /**
     * <p>
     *     Concatenates condition with and
     * </p>
     * @param conditions array of conditions those must be concatenated
     * @return string containing conditions concatenated with and
     */
    String and(String... conditions);

    /**
     * <p>
     *     Adds LEFT JOIN to SQL query
     * </p>
     * @param tableName name of table to join
     * @param condition join condition
     * @return {@link IQueryBuilder}
     */
    IQueryBuilder leftJoin(String tableName, String condition);

    /**
     * <p>
     *     Adds GROUP BY to SQL query
     * </p>
     * @param columns array of columns to group by
     * @return {@link IQueryBuilder}
     */
    IQueryBuilder groupBy(String... columns);

    /**
     * <p>
     *     Adds ORDER BY to SQL query
     * </p>
     * @param columns array of column to order by
     * @return {@link IQueryBuilder}
     */
    IQueryBuilder orderBy(String... columns);

    /**
     * <p>
     *     Generates SQL string
     * </p>
     * @return generated SQL string
     */
    String generateQuery();

}
