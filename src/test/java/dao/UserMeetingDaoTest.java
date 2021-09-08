package dao;

import com.conferences.dao.abstraction.IMeetingDao;
import com.conferences.dao.abstraction.IRoleDao;
import com.conferences.dao.abstraction.IUserDao;
import com.conferences.dao.abstraction.IUserMeetingDao;
import com.conferences.dao.implementation.MeetingDao;
import com.conferences.dao.implementation.RoleDao;
import com.conferences.dao.implementation.UserDao;
import com.conferences.dao.implementation.UserMeetingDao;
import com.conferences.entity.Meeting;
import com.conferences.entity.User;
import com.conferences.entity.UserMeeting;
import org.junit.BeforeClass;
import org.junit.Test;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UserMeetingDaoTest {

    private static IUserMeetingDao userMeetingDao;
    private static int userRoleId;
    private static int userId;
    private static int meetingId;

    @BeforeClass
    public static void beforeTest() {
        userMeetingDao = new UserMeetingDao();
        IRoleDao roleDao = new RoleDao();
        userRoleId = roleDao.findByTitle("user").getId();

        initDatabase();
    }

    @Test
    public void shouldFindCorrectUserMeeting() {
        clearUsersMeetingsTable();
        Meeting meeting = generateMeeting();

        IUserDao userDao = new UserDao();
        User user = userDao.findAllByRole("user").get(0);

        IMeetingDao meetingDao = new MeetingDao();
        meetingDao.create(meeting);

        UserMeeting userMeeting = new UserMeeting();
        userMeeting.setMeetingId(meeting.getId());
        userMeeting.setUserId(user.getId());

        userMeetingDao.create(userMeeting);

        UserMeeting databaseUserMeeting = userMeetingDao.findByUserIdAndMeetingId(userMeeting.getUserId(), userMeeting.getMeetingId());
        assertNotEquals(0, databaseUserMeeting.getId());
        assertNotEquals(0, databaseUserMeeting.getUserId());
        assertNotEquals(0, databaseUserMeeting.getMeetingId());
    }

    @Test
    public void shouldFindUsersForMeeting() {
        IUserDao userDao = new UserDao();
        List<User> users = userDao.findAllByRole("user");
        List<UserMeeting> usersMeetings = new ArrayList<>();
        SecureRandom random = new SecureRandom();
        for (User user: users) {
            UserMeeting userMeeting = new UserMeeting();
            userMeeting.setUserId(user.getId());
            userMeeting.setMeetingId(meetingId);
            userMeeting.setPresent(random.nextBoolean());
            usersMeetings.add(userMeeting);

            userMeetingDao.create(userMeeting);
        }
        List<UserMeeting> databaseUsersMeetings = userMeetingDao.findUserWithPresenceByMeetingId(meetingId);
        for (int i = 0; i < usersMeetings.size(); i++) {
            assertEquals(usersMeetings.get(i).getId(), databaseUsersMeetings.get(i).getId());
            assertEquals(usersMeetings.get(i).getMeetingId(), meetingId);
            assertEquals(usersMeetings.get(i).getUserId(), databaseUsersMeetings.get(i).getUserId());
            assertEquals(usersMeetings.get(i).isPresent(), databaseUsersMeetings.get(i).isPresent());
        }
    }

    @Test
    public void shouldUpdateUserPresence() {
        clearUsersMeetingsTable();

        UserMeeting userMeeting = new UserMeeting();
        userMeeting.setUserId(userId);
        userMeeting.setMeetingId(meetingId);
        userMeeting.setPresent(true);
        userMeetingDao.create(userMeeting);
        userMeeting.setPresent(false);
        userMeetingDao.updateUserPresenceByUserIdAndMeetingId(userMeeting);
        userMeeting = userMeetingDao.findByUserIdAndMeetingId(userId, meetingId);
        assertFalse(userMeeting.isPresent());
    }

    private static void initDatabase() {
        clearUsersMeetingsTable();

        meetingId = getMeetingId();
        userId = getUserId();
    }

    private static int getMeetingId() {
        IMeetingDao meetingDao = new MeetingDao();
        List<Meeting> meetings = meetingDao.findAll();
        if (meetings.isEmpty()) {
            Meeting meeting = generateMeeting();
            meetingDao.create(meeting);
            return meeting.getId();
        }
        return meetings.get(0).getId();
    }

    private static int getUserId() {
        IUserDao userDao = new UserDao();
        List<User> users = userDao.findAllByRole("user");
        if (users.isEmpty()) {
            User user = generateUser(userRoleId);
            userDao.create(user);
            return user.getId();
        }
        return users.get(0).getId();
    }

    private static User generateUser(int roleId) {
        User user = new User();
        user.setLogin("login");
        user.setRoleId(roleId);
        user.setPassword("$2a$10$9ls0FNi53FZEdVUSEzT.QOvQZcoJ24jps4QEKb5YBZIMaPHFPD5.K");
        user.setName("name");
        user.setSurname("surname");
        user.setEmail("email@example.com");
        user.setImagePath("user_image_path.png");
        return user;
    }

    private static Meeting generateMeeting() {
        Meeting meeting = new Meeting();
        meeting.setTitle("Meeting title");
        meeting.setDate(LocalDateTime.now().plusDays(5));
        meeting.setAddress("Meeting address");
        meeting.setDescription("Meeting description");
        meeting.setImagePath("meeting_image_path.jpg");
        return meeting;
    }

    private static void clearUsersMeetingsTable() {
        List<UserMeeting> usersMeetings = userMeetingDao.findAll();
        for (UserMeeting userMeeting: usersMeetings) {
            userMeetingDao.delete(userMeeting.getId());
        }
    }
}
