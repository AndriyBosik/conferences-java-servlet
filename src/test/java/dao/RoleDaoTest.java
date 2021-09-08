package dao;

import com.conferences.dao.abstraction.IRoleDao;
import com.conferences.dao.implementation.RoleDao;
import com.conferences.entity.Role;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class RoleDaoTest {

    private static IRoleDao roleDao;

    @BeforeClass
    public static void beforeTest() {
        roleDao = new RoleDao();
    }

    @Test
    public void shouldFindRoleByTitle() {
        Role role = roleDao.findByTitle("speaker");
        assertEquals(2, role.getId());
        assertEquals("speaker", role.getTitle());
    }

    @Test
    public void shouldReturnNullForNonExistingRole() {
        Role role = roleDao.findByTitle("guest");
        assertNull(role);
    }
}
