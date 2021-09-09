package com.conferences.dao.abstraction;

import com.conferences.entity.User;

import java.util.List;

/**
 * <p>
 *     Defines methods to process users table
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public interface IUserDao extends ICrudDao<Integer, User> {

    /**
     * <p>
     *     Retrieves {@link User} object with its role by login
     * </p>
     * @param login login value to search {@link User} by
     * @return found {@link User} object or {@code null} if no object was found
     */
    User findByLoginWithRole(String login);

    /**
     * <p>
     *     Retrieves {@link User} object by login or email
     * </p>
     * @param login login value to search {@link User} by
     * @param email email value to search {@link User} by
     * @return found {@link User} object or {@code null} if no object was founc
     */
    User findByLoginOrEmail(String login, String email);

    /**
     * <p>
     *     Retrieves all users by role
     * </p>
     * @param role name of role to get users by
     * @return list of users
     */
    List<User> findAllByRole(String role);

    /**
     * <p>
     *     Finds all speakers which are available to propose for specific topic
     * </p>
     * @param topicId id of topic
     * @return list of speakers
     */
    List<User> findAvailableSpeakersForProposalByTopic(int topicId);

    /**
     * <p>
     *     Updates user's image_path
     * </p>
     * @param user user with updated image_path
     * @return true if image_path was successfully updates, false otherwise
     */
    boolean updateUserImagePath(User user);
}
