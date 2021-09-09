package com.conferences.service.abstraction;

import com.conferences.entity.User;
import com.conferences.model.FormError;

import java.util.List;

/**
 * <p>
 *     Defines functions to process user Auth
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public interface IAuthService {

    /**
     * <p>
     *     Gets user from database by login and password
     * </p>
     * @param login user login
     * @param password user password
     * @return user from database, if one was found, null otherwise
     */
    User getByLoginAndPasswordWithRole(String login, String password);

    /**
     * <p>
     *     Register user to database
     * </p>
     * @param user user which should by registered
     * @return errors that may occur while signing up user
     */
    List<FormError> signUpUser(User user);
}
