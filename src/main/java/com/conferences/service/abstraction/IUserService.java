package com.conferences.service.abstraction;

import com.conferences.entity.User;
import com.conferences.entity.Role;
import com.conferences.model.FormError;

import java.util.List;

/**
 * <p>
 *      Defines functions to interact with user data
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public interface IUserService {

    /**
     * <p>
     *     Returns users by role title with {@link Role}
     * </p>
     * @param roleTitle title of role to get user for
     * @return list of {@link User} with their {@link Role}
     */
    List<User> getUsersByRoleTitleWithRole(String roleTitle);

    /**
     * <p>
     *     Updates user image path(avatar)
     * </p>
     * @param user user with his updated image path
     * @return true if image was updated, false otherwise
     */
    boolean updateUserImagePath(User user);

    /**
     * <p>
     *     Updates user data
     * </p>
     * @param user user with updated data
     * @return list of errors which may occur during user updating
     */
    List<FormError> updateUser(User user);
}
