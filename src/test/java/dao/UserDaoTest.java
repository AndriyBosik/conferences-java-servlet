package dao;

import com.conferences.dao.abstraction.IRoleDao;
import com.conferences.dao.abstraction.IUserDao;
import com.conferences.dao.implementation.RoleDao;
import com.conferences.dao.implementation.UserDao;
import com.conferences.entity.User;
import custom.util.Generator;
import custom.util.RandomUtil;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class UserDaoTest {

    private static IUserDao userDao;
    private static List<User> users;
    private static int moderatorRoleId;
    private static int speakerRoleId;
    private static int userRoleId;

    @BeforeClass
    public static void beforeTest() {
        userDao = new UserDao();
        IRoleDao roleDao = new RoleDao();
        moderatorRoleId = roleDao.findByTitle("moderator").getId();
        speakerRoleId = roleDao.findByTitle("speaker").getId();
        userRoleId = roleDao.findByTitle("user").getId();

        initDatabase();
    }

    @AfterClass
    public static void afterTest() {
        clearUsersTable();
    }

    @Test
    public void shouldFindUserByLoginWithRole() {
        User moderator = users.stream().filter(user -> user.getRoleId() == moderatorRoleId).findFirst().get();
        User user = userDao.findByLoginWithRole(moderator.getLogin());
        assertEquals(moderator.getLogin(), user.getLogin());
        assertEquals(moderator.getRoleId(), user.getRoleId());
        assertEquals(moderator.getPassword(), user.getPassword());
        assertEquals(moderator.getName(), user.getName());
        assertEquals(moderator.getSurname(), user.getSurname());
        assertEquals(moderator.getEmail(), user.getEmail());
        assertEquals(moderator.getImagePath(), user.getImagePath());
    }

    @Test
    public void shouldReturnNullForNotExistingLogin() {
        User user = userDao.findByLoginWithRole("");
        assertNull(user);
    }

    @Test
    public void shouldFindByLoginOrEmail() {
        for (User user: users) {
            User databaseUser = userDao.findByLoginOrEmail(user.getLogin(), user.getEmail());
            assertEquals(user.getLogin(), databaseUser.getLogin());
            assertEquals(user.getRoleId(), databaseUser.getRoleId());
            assertEquals(user.getPassword(), databaseUser.getPassword());
            assertEquals(user.getName(), databaseUser.getName());
            assertEquals(user.getSurname(), databaseUser.getSurname());
            assertEquals(user.getEmail(), databaseUser.getEmail());
            assertEquals(user.getImagePath(), databaseUser.getImagePath());
        }
    }

    @Test
    public void shouldFindAllUsersByRole() {
        List<User> usersRole = users.stream().filter(user -> user.getRoleId() == userRoleId).collect(Collectors.toList());
        List<User> databaseUsers = userDao.findAllByRole("user");
        for (int i = 0; i < usersRole.size(); i++) {
            assertEquals(usersRole.get(i).getLogin(), databaseUsers.get(i).getLogin());
            assertEquals(usersRole.get(i).getRoleId(), databaseUsers.get(i).getRoleId());
            assertEquals(usersRole.get(i).getPassword(), databaseUsers.get(i).getPassword());
            assertEquals(usersRole.get(i).getName(), databaseUsers.get(i).getName());
            assertEquals(usersRole.get(i).getSurname(), databaseUsers.get(i).getSurname());
            assertEquals(usersRole.get(i).getEmail(), databaseUsers.get(i).getEmail());
            assertEquals(usersRole.get(i).getImagePath(), databaseUsers.get(i).getImagePath());
        }
    }

    @Test
    public void shouldReturnAvailableSpeakersForProposalByTopic() {
        List<User> availableSpeakers = userDao.findAvailableSpeakersForProposalByTopic(1);
        List<User> speakers = users.stream().filter(user -> user.getRoleId() == speakerRoleId).collect(Collectors.toList());
        for (int i = 0; i < speakers.size(); i++) {
            assertEquals(speakers.get(i).getId(), availableSpeakers.get(i).getId());
        }
    }

    @Test
    public void shouldUpdateUserImagePath() {
        User user = users.get(users.size() - 1);
        user.setImagePath("new_user_image_path.png");
        userDao.updateUserImagePath(user);
        User databaseUser = userDao.find(user.getId());
        assertEquals(user.getImagePath(), databaseUser.getImagePath());
    }

    private static void initDatabase() {
        clearUsersTable();

        users = new ArrayList<>();
        generateUsers();
    }

    private static void clearUsersTable() {
        List<User> users = userDao.findAll();
        for (User user: users) {
            userDao.delete(user.getId());
        }
    }

    private static void generateUsers() {
        User user;
        user = Generator.generateUser(moderatorRoleId);
        userDao.create(user);
        users.add(user);

        user = Generator.generateUser(speakerRoleId);
        userDao.create(user);
        users.add(user);

        user = Generator.generateUser(userRoleId);
        userDao.create(user);
        users.add(user);

        user = Generator.generateUser(speakerRoleId);
        userDao.create(user);
        users.add(user);

        user = Generator.generateUser(userRoleId);
        userDao.create(user);
        users.add(user);

        user = Generator.generateUser(userRoleId);
        userDao.create(user);
        users.add(user);

        user = Generator.generateUser(userRoleId);
        userDao.create(user);
        users.add(user);
    }
}
