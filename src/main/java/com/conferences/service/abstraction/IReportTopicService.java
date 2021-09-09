package com.conferences.service.abstraction;

import com.conferences.entity.ReportTopic;
import com.conferences.entity.ReportTopicSpeaker;
import com.conferences.model.FormError;

import java.util.List;

/**
 * <p>
 *     Defines methods to process {@link ReportTopic} data
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public interface IReportTopicService {

    /**
     * <p>
     *     Updates topic with its speaker
     * </p>
     * @param reportTopic topic to be updated
     * @return list of errors if such were occurred while updating
     */
    List<FormError> updateTopicWithSpeaker(ReportTopic reportTopic);

    /**
     * <p>
     *     Assigns speaker for topic
     * </p>
     * @param reportTopicSpeaker data about topic id and assigned speaker id
     * @return true if speaker was assigned, false otherwise
     */
    boolean setSpeakerForTopic(ReportTopicSpeaker reportTopicSpeaker);

    /**
     * <p>
     *     Saves {@link ReportTopic} with its speaker
     * </p>
     * @param reportTopic topic to be saved
     * @return list of errors which may occur while saving a topic
     */
    List<FormError> saveWithSpeaker(ReportTopic reportTopic);

}
