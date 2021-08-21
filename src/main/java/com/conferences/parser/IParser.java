package com.conferences.parser;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IParser<T> {

    T parseToModel(ResultSet result) throws SQLException;

    T parseToModel(ResultSet result, String prefix) throws SQLException;

}
