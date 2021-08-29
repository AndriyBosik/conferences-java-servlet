package com.conferences.dao.abstraction;

import com.conferences.entity.Role;

public interface IRoleDao extends IDao<Integer, Role> {

    Role findByTitle(String title);

}
