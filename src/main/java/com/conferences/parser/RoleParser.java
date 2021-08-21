package com.conferences.parser;

import com.conferences.model.Role;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleParser implements IParser<Role> {
    @Override
    public Role parseToModel(ResultSet result) throws SQLException {
        return parseToModel(result, "");
    }

    @Override
    public Role parseToModel(ResultSet result, String prefix) throws SQLException {
        Role role = new Role();
        role.setId(result.getInt(prefix + "id"));
        role.setTitle(result.getString(prefix + "title"));
        return role;
    }
}
