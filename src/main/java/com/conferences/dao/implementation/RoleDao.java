package com.conferences.dao.implementation;

import com.conferences.dao.abstraction.AbstractDao;
import com.conferences.dao.abstraction.IRoleDao;
import com.conferences.entity.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RoleDao extends AbstractDao<Integer, Role> implements IRoleDao {
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String TABLE_NAME = "roles";

}
