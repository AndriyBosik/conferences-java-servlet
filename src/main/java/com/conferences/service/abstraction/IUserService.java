package com.conferences.service.abstraction;

import com.conferences.entity.User;
import com.conferences.model.FormError;

import java.util.List;

public interface IUserService {

    List<User> getUsersByRoleTitleWithRole(String roleTitle);

    boolean updateUserImagePath(User user);

    List<FormError> updateUser(User user);

}
