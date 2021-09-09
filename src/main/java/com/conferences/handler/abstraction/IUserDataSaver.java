package com.conferences.handler.abstraction;

import com.conferences.entity.User;
import com.conferences.model.UserData;

/**
 * <p>
 *     Defines methods to process user data
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public interface IUserDataSaver {

    /**
     * <p>
     *     Saves {@link User} data into {@link UserData} object
     * </p>
     * @param user model which data must be saved
     * @return {@link UserData} object with saved {@link User} data
     */
    UserData saveUserData(User user);

    /**
     * <p>
     *     Restores {@link User} data from {@link UserData} object
     * </p>
     * @param user model to restore data to
     * @param data model to get data from
     */
    void restoreUserData(User user, UserData data);
}
