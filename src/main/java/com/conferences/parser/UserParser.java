package com.conferences.parser;

import com.conferences.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserParser implements IParser<User> {
    @Override
    public User parseToModel(ResultSet result) throws SQLException {
        return parseToModel(result, "");
    }

    @Override
    public User parseToModel(ResultSet result, String prefix) throws SQLException {
        User user = new User();
        user.setId(result.getInt(prefix + "id"));
        user.setLogin(result.getString(prefix + "login"));
        user.setPassword(result.getString(prefix + "password"));
        user.setRoleId(result.getInt(prefix + "role_id"));
        return user;
    }
}
