package com.conferences.service.abstraction;

import com.conferences.entity.User;

import java.util.List;

public interface IUserService {

    User getByLoginAndPasswordWithRole(String login, String password);

    List<User> getUsersByRoleTitleWithRole(String roleTitle);

    boolean signUpUser(User user);

    boolean updateUserImagePath(User user);

    boolean updateUser(User user);

}
