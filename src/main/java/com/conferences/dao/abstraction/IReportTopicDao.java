package com.conferences.dao.abstraction;

import com.conferences.entity.ReportTopic;

/**
 * <p>
 *     Defines methods to process report_topics table
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public interface IReportTopicDao extends ICrudDao<Integer, ReportTopic> {

    /**
     * <p>
     *     Updates {@link ReportTopic} with its speaker
     * </p>
     * @param reportTopic entity which contains data about report topic and speaker
     * @return true if entity was successfully updated, false otherwise
     */
    boolean updateWithSpeaker(ReportTopic reportTopic);

    /**
     * <p>
     *     Saves {@link ReportTopic} with its speaker
     * </p>
     * @param reportTopic entity which contains data about report topic and speaker
     * @return true if entity was successfully saved, false otherwise
     */
    boolean saveWithSpeaker(ReportTopic reportTopic);
}
