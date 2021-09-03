package com.conferences.service.implementation;

import com.conferences.dao.abstraction.IRoleDao;
import com.conferences.dao.implementation.RoleDao;
import com.conferences.entity.Role;
import com.conferences.factory.DaoFactory;
import com.conferences.service.abstraction.IRoleService;

public class RoleService implements IRoleService {

    private IRoleDao roleDao;

    public RoleService() {
        this.roleDao = DaoFactory.getInstance().getRoleDao();
    }

    @Override
    public Role getRoleByTitle(String title) {
        return roleDao.findByTitle(title);
    }
}
