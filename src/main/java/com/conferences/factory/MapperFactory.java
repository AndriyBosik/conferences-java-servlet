package com.conferences.factory;

import com.conferences.entity.Meeting;
import com.conferences.entity.ReportTopic;
import com.conferences.entity.TopicProposal;
import com.conferences.mapper.*;
import com.conferences.model.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 *     Defines methods to instantiate mappers
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public class MapperFactory {

    private static MapperFactory instance;

    private IMapper<Map<String, String>, Meeting> formDataToMeetingMapper;
    private IMapper<FormError, String> formErrorToStringMapper;
    private IMapper<HttpServletRequest, Meeting> requestToEditableMeetingDataMapper;
    private IMapper<HttpServletRequest, FileFormData<Meeting>> requestToFileFormMeetingMapper;
    private IMapper<HttpServletRequest, LoginData> requestToLoginDataMapper;
    private IMapper<HttpServletRequest, MeetingSorter> requestToMeetingSorterMapper;
    private IMapper<HttpServletRequest, PasswordData> requestToPasswordDataMapper;
    private IMapper<HttpServletRequest, ReportTopic> requestToReportTopicWithSpeakerMapper;
    private IMapper<HttpServletRequest, TopicProposal> requestToTopicProposalMapper;
    private IMapper<HttpServletRequest, UserData> requestToUserDataMapper;
    private IMapper<HttpServletRequest, FileFormData<Map<String, String>>> simpleFileFormMapper;
    private IMapper<TopicProposal, ReportTopic> topicProposalToReportTopicMapper;
    private IMapper<String, String> urlToJavaCommandNameMapper;

    private MapperFactory() {}

    public static synchronized MapperFactory getInstance() {
        if (instance == null) {
            instance = new MapperFactory();
        }
        return instance;
    }

    public synchronized IMapper<Map<String, String>, Meeting> getFormDataToMeetingMapper() {
        if (formDataToMeetingMapper == null) {
            formDataToMeetingMapper = new FormDataToMeetingMapper();
        }
        return formDataToMeetingMapper;
    }

    public synchronized IMapper<FormError, String> getFormErrorToStringMapper() {
        if (formErrorToStringMapper == null) {
            formErrorToStringMapper = new FormErrorToStringMapper();
        }
        return formErrorToStringMapper;
    }

    public synchronized IMapper<HttpServletRequest, Meeting> getRequestToEditableMeetingDataMapper() {
        if (requestToEditableMeetingDataMapper == null) {
            requestToEditableMeetingDataMapper = new RequestToEditableMeetingDataMapper();
        }
        return requestToEditableMeetingDataMapper;
    }

    public synchronized IMapper<HttpServletRequest, FileFormData<Meeting>> getRequestToFileFormMeetingMapper() {
        if (requestToFileFormMeetingMapper == null) {
            requestToFileFormMeetingMapper = new RequestToFileFormMeetingMapper();
        }
        return requestToFileFormMeetingMapper;
    }

    public synchronized IMapper<HttpServletRequest, LoginData> getRequestToLoginDataMapper() {
        if (requestToLoginDataMapper == null) {
            requestToLoginDataMapper = new RequestToLoginDataMapper();
        }
        return requestToLoginDataMapper;
    }

    public synchronized IMapper<HttpServletRequest, MeetingSorter> getRequestToMeetingSorterMapper() {
        if (requestToMeetingSorterMapper == null) {
            requestToMeetingSorterMapper = new RequestToMeetingSorterMapper();
        }
        return requestToMeetingSorterMapper;
    }

    public synchronized IMapper<HttpServletRequest, PasswordData> getRequestToPasswordDataMapper() {
        if (requestToPasswordDataMapper == null) {
            requestToPasswordDataMapper = new RequestToPasswordDataMapper();
        }
        return requestToPasswordDataMapper;
    }

    public synchronized IMapper<HttpServletRequest, ReportTopic> getRequestToReportTopicWithSpeakerMapper() {
        if (requestToReportTopicWithSpeakerMapper == null) {
            requestToReportTopicWithSpeakerMapper = new RequestToReportTopicWithSpeakerMapper();
        }
        return requestToReportTopicWithSpeakerMapper;
    }

    public synchronized IMapper<HttpServletRequest, TopicProposal> getRequestToTopicProposalMapper() {
        if (requestToTopicProposalMapper == null) {
            requestToTopicProposalMapper = new RequestToTopicProposalMapper();
        }
        return requestToTopicProposalMapper;
    }

    public synchronized IMapper<HttpServletRequest, UserData> getRequestToUserDataMapper() {
        if (requestToUserDataMapper == null) {
            requestToUserDataMapper = new RequestToUserDataMapper();
        }
        return requestToUserDataMapper;
    }

    public synchronized IMapper<HttpServletRequest, FileFormData<Map<String, String>>> getSimpleFileFormMapper() {
        if (simpleFileFormMapper == null) {
            simpleFileFormMapper = new SimpleFileFormMapper();
        }
        return simpleFileFormMapper;
    }

    public synchronized IMapper<TopicProposal, ReportTopic> getTopicProposalToReportTopicMapper() {
        if (topicProposalToReportTopicMapper == null) {
            topicProposalToReportTopicMapper = new TopicProposalToReportTopicMapper();
        }
        return topicProposalToReportTopicMapper;
    }

    public synchronized IMapper<String, String> getUrlToJavaCommandNameMapper() {
        if (urlToJavaCommandNameMapper == null) {
            urlToJavaCommandNameMapper = new UrlToJavaCommandNameMapper();
        }
        return urlToJavaCommandNameMapper;
    }
}
