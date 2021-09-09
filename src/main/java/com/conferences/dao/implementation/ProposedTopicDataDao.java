package com.conferences.dao.implementation;

import com.conferences.config.DbManager;
import com.conferences.dao.abstraction.AbstractDao;
import com.conferences.dao.abstraction.IProposedTopicDataDao;
import com.conferences.entity.custom.ProposedTopicData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * {@inheritDoc}
 */
public class ProposedTopicDataDao extends AbstractDao<Integer, ProposedTopicData> implements IProposedTopicDataDao {

    private static final Logger LOGGER = LogManager.getLogger(ProposedTopicDataDao.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProposedTopicData> findAllProposedTopicsOrderByMeetingId() {
        String sql =
            "SELECT " +
                "tp.id AS id," +
                "m.id AS meeting_id," +
                "m.title AS meeting_title," +
                "m.date AS meeting_date," +
                "m.image_path AS meeting_image_path," +
                "tp.topic_title AS proposed_topic_title," +
                "u.name AS speaker_name," +
                "u.surname AS speaker_surname," +
                "u.image_path AS speaker_image_path " +
            "FROM topic_proposals tp " +
                "LEFT JOIN meetings m ON m.id=tp.meeting_id " +
                "LEFT JOIN users u ON u.id=tp.speaker_id " +
            "ORDER BY m.id, tp.id";
        List<ProposedTopicData> proposedTopics = new ArrayList<>();
        try (Connection connection = DbManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            LOGGER.info("Fetching all proposed topics. Sql: {}", sql);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                LOGGER.info("Parsing ProposedTopicData");
                proposedTopics.add(entityParser.parseToEntity(ProposedTopicData.class, result));
            }
        } catch (SQLException exception) {
            LOGGER.error("Unable to fetch", exception);
            proposedTopics.clear();
        }
        return proposedTopics;
    }
}
