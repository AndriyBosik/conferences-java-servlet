package com.conferences.service.implementation;

import com.conferences.dao.abstraction.IRoleDao;
import com.conferences.dao.implementation.RoleDao;
import com.conferences.entity.Role;
import com.conferences.factory.DaoFactory;
import com.conferences.service.abstraction.IRoleService;

/**
 * {@inheritDoc}
 */
public class RoleService implements IRoleService {

    private final IRoleDao roleDao;

    public RoleService() {
        this.roleDao = DaoFactory.getInstance().getRoleDao();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Role getRoleByTitle(String title) {
        return roleDao.findByTitle(title);
    }
}
