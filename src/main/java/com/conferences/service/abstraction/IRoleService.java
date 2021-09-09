package com.conferences.service.abstraction;

import com.conferences.entity.Role;

/**
 * <p>
 *     Defines methods to process {@link Role} data
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public interface IRoleService {

    /**
     * <p>
     *     Returns {@link Role} object by title
     * </p>
     * @param title title of role
     * @return {@link Role}
     */
    Role getRoleByTitle(String title);
}
