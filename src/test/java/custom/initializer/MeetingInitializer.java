package custom.initializer;

import com.conferences.config.MeetingFilterSelector;
import com.conferences.config.MeetingSortOption;
import com.conferences.config.SortOrder;
import com.conferences.dao.abstraction.*;
import com.conferences.dao.implementation.*;
import com.conferences.entity.*;
import com.conferences.model.MeetingSorter;
import com.conferences.model.Page;
import com.conferences.model.PageResponse;
import custom.util.Cleaner;
import custom.util.Generator;
import custom.util.RandomUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MeetingInitializer {

    private List<User> users;
    private List<User> speakers;
    private Map<Integer, Meeting> meetings;
    private Map<Integer, List<ReportTopic>> reportTopics;
    private List<UserMeeting> usersMeetings;
    private Map<Integer, ReportTopicSpeaker> reportTopicSpeakers;
    private IUserDao userDao;
    private IMeetingDao meetingDao;
    private IReportTopicDao reportTopicDao;
    private IUserMeetingDao userMeetingDao;
    private IReportTopicSpeakerDao reportTopicSpeakerDao;
    private int userRoleId;
    private int speakerRoleId;

    public void init() {
        IRoleDao roleDao = new RoleDao();
        userRoleId = roleDao.findByTitle("user").getId();
        speakerRoleId = roleDao.findByTitle("speaker").getId();

        Cleaner.clearUsersTable();
        Cleaner.clearMeetingsTable();
        Cleaner.clearReportTopicsTable();
        Cleaner.clearUsersMeetingsTable();
        Cleaner.clearReportTopicsSpeakersTable();

        users = new ArrayList<>();
        speakers = new ArrayList<>();
        meetings = new HashMap<>();
        reportTopics = new HashMap<>();
        usersMeetings = new ArrayList<>();
        reportTopicSpeakers = new HashMap<>();

        userDao = new UserDao();
        meetingDao = new MeetingDao();
        reportTopicDao = new ReportTopicDao();
        userMeetingDao = new UserMeetingDao();
        reportTopicSpeakerDao = new ReportTopicSpeakerDao();

        addMeetings();
        addUsers();
        addUsersMeetings();
        addSpeakers();
        addReportTopics();
        addReportTopicsSpeakers();
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Meeting> getMeetings() {
        return new ArrayList<>(meetings.values());
    }

    private void addMeetings() {
        for (int i = 0; i < 10; i++) {
            Meeting meeting = Generator.generateMeeting();
            meetingDao.create(meeting);
            meetings.put(meeting.getId(), meeting);
        }
    }

    private void addUsers() {
        for (int i = 0; i < 7; i++) {
            User user = Generator.generateUser(userRoleId);
            userDao.create(user);
            users.add(user);
        }
    }

    private void addUsersMeetings() {
        List<Meeting> meetingsValues = new ArrayList<>(meetings.values());
        for (User user: users) {
            for (Meeting meeting: meetingsValues) {
                boolean add = RandomUtil.generateInt(2) == 0;
                if (add) {
                    UserMeeting userMeeting = new UserMeeting();
                    userMeeting.setUserId(user.getId());
                    userMeeting.setMeetingId(meeting.getId());
                    userMeeting.setPresent(RandomUtil.generateInt(2) == 0);

                    usersMeetings.add(userMeeting);
                    userMeetingDao.create(userMeeting);
                }
            }
        }
    }

    private void addSpeakers() {
        for (int i = 0; i < 5; i++) {
            User speaker = Generator.generateUser(speakerRoleId);
            userDao.create(speaker);
            speakers.add(speaker);
        }
    }

    private void addReportTopics() {
        List<Integer> meetingIds = new ArrayList<>(meetings.keySet());
        for (int meetingId: meetingIds) {
            int reportTopicsCount = RandomUtil.generateInt(12);
            for (int i = 0; i < reportTopicsCount; i++) {
                ReportTopic reportTopic = Generator.generateReportTopic(meetingId);
                reportTopicDao.create(reportTopic);
                reportTopics.computeIfAbsent(reportTopic.getMeetingId(), k -> new ArrayList<>());
                reportTopics.get(reportTopic.getMeetingId()).add(reportTopic);
            }
        }
    }

    private void addReportTopicsSpeakers() {
        List<ReportTopic> reportTopicValues = reportTopics.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream())
                .collect(Collectors.toList());
        for (ReportTopic reportTopic: reportTopicValues) {
            for (User speaker: speakers) {
                boolean add = RandomUtil.generateInt(2) == 0;
                if (add) {
                    ReportTopicSpeaker reportTopicSpeaker = new ReportTopicSpeaker();
                    reportTopicSpeaker.setReportTopicId(reportTopic.getId());
                    reportTopicSpeaker.setSpeakerId(speaker.getId());
                    reportTopicSpeaker.setSpeaker(speaker);
                    reportTopicSpeakerDao.create(reportTopicSpeaker);

                    reportTopic.setReportTopicSpeaker(reportTopicSpeaker);
                    break;
                }
            }
        }
    }

    public Meeting findByKeyWithReportTopicsAndSpeakersAndUsersCount(int meetingId) {
        Meeting meeting = meetings.get(meetingId);
        meeting.setReportTopics(reportTopics.get(meetingId));
        meeting.setUsersCount((int) usersMeetings.stream()
            .filter(userMeeting -> userMeeting.getMeetingId() == meetingId)
            .count());
        return meeting;
    }

    public PageResponse<Meeting> findAllPageBySorterWithUsersCountAndTopicsCount(Page page, MeetingSorter sorter) {
        PageResponse<Meeting> response = new PageResponse<>();
        List<Meeting> meetingsList = meetings.values().stream()
                .filter(meeting -> filterMeeting(meeting, sorter.getFilterSelector()))
                .sorted((first, second) -> sortBySortOption(first, second, sorter.getSortOption(), sorter.getSortOrder()))
                .skip((long)(page.getPageNumber() - 1)*page.getItemsCount())
                .limit(page.getItemsCount())
                .collect(Collectors.toList());
        for (Meeting meeting: meetingsList) {
            meeting.setReportTopics(reportTopics.get(meeting.getId()));
            meeting.setUsersCount((int) usersMeetings.stream()
                .filter(userMeeting -> userMeeting.getMeetingId() == meeting.getId())
                .count());
        }

        response.setItems(meetingsList);
        response.setPageSize(page.getItemsCount());
        response.setTotalItems(meetings.values().size());
        return response;
    }

    private int sortBySortOption(Meeting first, Meeting second, MeetingSortOption option, SortOrder order) {
        int k = -1;
        if (order == SortOrder.DESC) {
            k = 1;
        }
        switch (option) {
            case TOPICS:
                return k*(first.getReportTopicsCount() - second.getReportTopicsCount());
            case USERS:
                return k*(first.getUsersCount() - second.getUsersCount());
            case DATE:
                return k*(first.getDate().compareTo(second.getDate()));
        }
        return 0;
    }

    private boolean filterMeeting(Meeting meeting, MeetingFilterSelector selector) {
        switch (selector) {
            case PAST:
                return meeting.getDate().isBefore(LocalDateTime.now());
            case FUTURE:
                return meeting.getDate().isAfter(LocalDateTime.now());
        }
        return true;
    }
}
