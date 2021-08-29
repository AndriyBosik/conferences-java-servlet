package com.conferences.service.abstraction;

import com.conferences.entity.Role;

public interface IRoleService {

    Role getRoleByTitle(String title);

}
