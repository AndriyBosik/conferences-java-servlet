package dao;

import com.conferences.dao.abstraction.ISpeakerProposalDao;
import com.conferences.dao.implementation.SpeakerProposalDao;
import com.conferences.entity.SpeakerProposal;
import custom.initializer.SpeakerProposalDataInitializer;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class SpeakerProposalDaoTest {

    private static ISpeakerProposalDao speakerProposalDao;
    private static SpeakerProposalDataInitializer initializer;

    @BeforeClass
    public static void beforeTest() {
        speakerProposalDao = new SpeakerProposalDao();
        initializer = new SpeakerProposalDataInitializer();
        initializer.init();
    }

    @Test
    public void shouldFindAllByTopicIdWithSpeaker() {
        int reportTopicId = initializer.getReportTopics().get(0).getId();
        List<SpeakerProposal> databaseProposals = speakerProposalDao.findAllByTopicIdWithSpeaker(reportTopicId).stream()
            .sorted(Comparator.comparingInt(SpeakerProposal::getId))
            .collect(Collectors.toList());
        List<SpeakerProposal> proposals = initializer.getProposals().stream()
            .filter(proposal -> proposal.getReportTopicId() == reportTopicId)
            .sorted(Comparator.comparingInt(SpeakerProposal::getId))
            .collect(Collectors.toList());

        assertEquals(proposals.size(), databaseProposals.size());
        for (int i = 0; i < proposals.size(); i++) {
            assertEquals(proposals.get(i).getId(), databaseProposals.get(i).getId());
            assertEquals(proposals.get(i).getSpeakerId(), databaseProposals.get(i).getSpeakerId());
            assertEquals(proposals.get(i).getReportTopicId(), databaseProposals.get(i).getReportTopicId());
            assertNotNull(databaseProposals.get(i).getSpeaker());
            assertEquals(proposals.get(i).getSpeakerId(), databaseProposals.get(i).getSpeaker().getId());
        }
    }

    @Test
    public void shouldFindAllSpeakerProposedTopicIdsForMeeting() {
        int meetingId = initializer.getMeetings().get(0).getId();
        int speakerId = initializer.getSpeakers().get(0).getId();
        List<Integer> ids = initializer.getAllSpeakerProposedTopicIdsForMeeting(meetingId, speakerId);
        List<Integer> databaseIds = initializer.getAllSpeakerProposedTopicIdsForMeeting(meetingId, speakerId);
        assertEquals(ids.size(), databaseIds.size());
        for (int i = 0; i < ids.size(); i++) {
            assertEquals(ids.get(i), databaseIds.get(i));
        }
    }
}
