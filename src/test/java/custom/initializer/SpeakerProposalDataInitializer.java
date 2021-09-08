package custom.initializer;

import com.conferences.dao.abstraction.*;
import com.conferences.dao.implementation.*;
import com.conferences.entity.Meeting;
import com.conferences.entity.ReportTopic;
import com.conferences.entity.SpeakerProposal;
import com.conferences.entity.User;
import custom.util.Cleaner;
import custom.util.Generator;
import custom.util.RandomUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SpeakerProposalDataInitializer {

    private final IUserDao userDao;
    private final IMeetingDao meetingDao;
    private final IReportTopicDao reportTopicDao;
    private final ISpeakerProposalDao speakerProposalDao;

    private final List<User> speakers;
    private final List<Meeting> meetings;
    private final List<ReportTopic> reportTopics;
    private final List<SpeakerProposal> proposals;
    private int speakerRoleId;

    public SpeakerProposalDataInitializer() {
        userDao = new UserDao();
        meetingDao = new MeetingDao();
        reportTopicDao = new ReportTopicDao();
        speakerProposalDao = new SpeakerProposalDao();

        speakers = new ArrayList<>();
        meetings = new ArrayList<>();
        reportTopics = new ArrayList<>();
        proposals = new ArrayList<>();
    }

    public void init() {
        IRoleDao roleDao = new RoleDao();
        speakerRoleId = roleDao.findByTitle("speaker").getId();
        Cleaner.clearSpeakerProposalsTable();
        Cleaner.clearReportTopicsTable();
        Cleaner.clearUsersTable();

        addSpeakers();
        addMeetings();
        addReportTopics();
        addSpeakerProposals();
    }

    public List<User> getSpeakers() {
        return speakers;
    }

    public List<Meeting> getMeetings() {
        return meetings;
    }

    public List<ReportTopic> getReportTopics() {
        return reportTopics;
    }

    public List<SpeakerProposal> getProposals() {
        return proposals;
    }

    public List<Integer> getAllSpeakerProposedTopicIdsForMeeting(int meetingId, int speakerId) {
        List<Integer> reportTopicIds = reportTopics.stream()
            .filter(reportTopic -> reportTopic.getMeetingId() == meetingId)
            .map(ReportTopic::getId)
            .sorted(Comparator.comparingInt(firstId -> firstId))
            .collect(Collectors.toList());
        return proposals.stream()
            .filter(proposal -> reportTopicIds.contains(proposal.getReportTopicId()))
            .map(SpeakerProposal::getSpeakerId)
            .sorted(Comparator.comparingInt(firstId -> firstId))
            .collect(Collectors.toList());
    }

    private void addSpeakers() {
        for (int i = 0; i < 10; i++) {
            User speaker = Generator.generateUser(speakerRoleId);
            speakers.add(speaker);
            userDao.create(speaker);
        }
    }

    private void addMeetings() {
        for (int i = 0; i < 10; i++) {
            Meeting meeting = Generator.generateMeeting();
            meetingDao.create(meeting);
            meetings.add(meeting);
        }
    }

    private void addReportTopics() {
        for (int i = 0; i < 15; i++) {
            int meetingId = meetings.get(RandomUtil.generateInt(meetings.size())).getId();
            ReportTopic reportTopic = Generator.generateReportTopic(meetingId);
            reportTopicDao.create(reportTopic);
            reportTopics.add(reportTopic);
        }
    }

    private void addSpeakerProposals() {
        for (User speaker : speakers) {
            for (ReportTopic reportTopic : reportTopics) {
                SpeakerProposal proposal = new SpeakerProposal();
                proposal.setSpeakerId(speaker.getId());
                proposal.setReportTopicId(reportTopic.getId());
                speakerProposalDao.create(proposal);
                proposals.add(proposal);
            }
        }
    }
}
