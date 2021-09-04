package com.conferences.service.abstraction;

import com.conferences.entity.User;
import com.conferences.model.FormError;

import java.util.List;

public interface IUserService {

    User getByLoginAndPasswordWithRole(String login, String password);

    List<User> getUsersByRoleTitleWithRole(String roleTitle);

    List<FormError> signUpUser(User user);

    boolean updateUserImagePath(User user);

    boolean updateUser(User user);

}
