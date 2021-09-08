package custom.util;

import com.conferences.entity.Meeting;
import com.conferences.entity.ReportTopic;
import com.conferences.entity.TopicProposal;
import com.conferences.entity.User;

import java.time.LocalDateTime;

public class Generator {

    public static Meeting generateMeeting() {
        Meeting meeting = new Meeting();
        meeting.setTitle(RandomUtil.generateRandomString(10));
        meeting.setDescription(RandomUtil.generateRandomString(50));
        meeting.setAddress(RandomUtil.generateRandomString(15));
        meeting.setImagePath(RandomUtil.generateRandomString(8));
        meeting.setDate(LocalDateTime.now().plusDays(4).plusHours(4).plusMinutes(10));
        return meeting;
    }

    public static User generateUser(int roleId) {
        User user = new User();
        user.setLogin(RandomUtil.generateRandomString(6));
        user.setRoleId(roleId);
        user.setPassword(RandomUtil.generateRandomString(12));
        user.setName(RandomUtil.generateRandomString(7));
        user.setSurname(RandomUtil.generateRandomString(9));
        user.setEmail(RandomUtil.generateRandomString(5) + "@" + RandomUtil.generateRandomString(3) + "." + RandomUtil.generateRandomString(2));
        user.setImagePath(RandomUtil.generateRandomString(8));
        return user;
    }

    public static TopicProposal generateTopicProposal(int speakerId, int meetingId) {
        TopicProposal topicProposal = new TopicProposal();
        topicProposal.setSpeakerId(speakerId);
        topicProposal.setMeetingId(meetingId);
        topicProposal.setTopicTitle(RandomUtil.generateRandomString(20));
        return topicProposal;
    }

    public static ReportTopic generateReportTopic(int meetingId) {
        ReportTopic reportTopic = new ReportTopic();
        reportTopic.setMeetingId(meetingId);
        reportTopic.setTitle(RandomUtil.generateRandomString(14));
        return reportTopic;
    }
}
