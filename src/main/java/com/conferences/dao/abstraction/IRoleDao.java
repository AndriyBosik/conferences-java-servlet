package com.conferences.dao.abstraction;

import com.conferences.entity.Role;

/**
 * <p>
 *     Defines methods to process roles table
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public interface IRoleDao extends ICrudDao<Integer, Role> {

    /**
     * <p>
     *     Retrieves {@link Role} by its title
     * </p>
     * @param title title to find role by
     * @return found role or null if no role was found
     */
    Role findByTitle(String title);
}
