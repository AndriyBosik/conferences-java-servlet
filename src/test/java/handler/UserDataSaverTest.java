package handler;

import com.conferences.entity.User;
import com.conferences.handler.abstraction.IUserDataSaver;
import com.conferences.handler.implementation.UserDataSaver;
import com.conferences.model.UserData;
import custom.util.Generator;
import custom.util.RandomUtil;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserDataSaverTest {

    private static IUserDataSaver userDataSaver;

    @BeforeClass
    public static void beforeTest() {
        userDataSaver = new UserDataSaver();
    }

    @Test
    public void shouldSaveUserData() {
        User user = Generator.generateUser(1);
        UserData userData = userDataSaver.saveUserData(user);
        assertEquals(user.getLogin(), userData.getLogin());
        assertEquals(user.getPassword(), userData.getPassword());
        assertEquals(user.getEmail(), userData.getEmail());
        assertEquals(user.getName(), userData.getName());
        assertEquals(user.getSurname(), userData.getSurname());
    }

    @Test
    public void shouldRestoreUserData() {
        UserData userData = new UserData();
        userData.setLogin(RandomUtil.generateRandomString(10));
        userData.setPassword(RandomUtil.generateRandomString(12));
        userData.setEmail(RandomUtil.generateEmail());
        userData.setName(RandomUtil.generateRandomString(6));
        userData.setSurname(RandomUtil.generateRandomString(8));

        User user = new User();
        userDataSaver.restoreUserData(user, userData);
        assertEquals(userData.getLogin(), user.getLogin());
        assertEquals(userData.getPassword(), user.getPassword());
        assertEquals(userData.getEmail(), user.getEmail());
        assertEquals(userData.getName(), user.getName());
        assertEquals(userData.getSurname(), user.getSurname());
    }
}
