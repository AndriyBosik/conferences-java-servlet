package com.conferences.dao.abstraction;

import com.conferences.entity.ReportTopicSpeaker;

/**
 * <p>
 *     Defines methods to process report_topics_speakers table
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public interface IReportTopicSpeakerDao extends ICrudDao<Integer, ReportTopicSpeaker> {

    /**
     * <p>
     *     Saves {@link ReportTopicSpeaker} to database and deletes all proposals fot assigned speaker
     * </p>
     * @param reportTopicSpeaker data which have to be saved
     * @return true if {@link ReportTopicSpeaker} was successfully saved, false otherwise
     */
    boolean saveWithProposalsDeletion(ReportTopicSpeaker reportTopicSpeaker);
}
