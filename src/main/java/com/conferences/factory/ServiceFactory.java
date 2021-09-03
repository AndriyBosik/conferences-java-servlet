package com.conferences.factory;

import com.conferences.service.abstraction.*;
import com.conferences.service.implementation.*;

public class ServiceFactory {

    private static ServiceFactory instance;

    private IMeetingService meetingService;
    private IModeratorProposalService moderatorProposalService;
    private IProposalDataService proposalDataService;
    private IProposedTopicDataService proposedTopicDataService;
    private IReportTopicService reportTopicService;
    private IRoleService roleService;
    private ISpeakerProposalService speakerProposalService;
    private ISpeakerService speakerService;
    private ITopicProposalService topicProposalService;
    private IUserService userService;

    private ServiceFactory() {}

    public static synchronized ServiceFactory getInstance() {
        if (instance == null) {
            instance = new ServiceFactory();
        }
        return instance;
    }

    public synchronized IMeetingService getMeetingService() {
        if (meetingService == null) {
            meetingService = new MeetingService();
        }
        return meetingService;
    }

    public synchronized IModeratorProposalService getModeratorProposalService() {
        if (moderatorProposalService == null) {
            moderatorProposalService = new ModeratorProposalService();
        }
        return moderatorProposalService;
    }

    public synchronized IProposalDataService getProposalDataService() {
        if (proposalDataService == null) {
            proposalDataService = new ProposalDataService();
        }
        return proposalDataService;
    }

    public synchronized IProposedTopicDataService getProposedTopicDataService() {
        if (proposedTopicDataService == null) {
            proposedTopicDataService = new ProposedTopicDataService();
        }
        return proposedTopicDataService;
    }

    public synchronized IReportTopicService getReportTopicService() {
        if (reportTopicService == null) {
            reportTopicService = new ReportTopicService();
        }
        return reportTopicService;
    }

    public synchronized IRoleService getRoleService() {
        if (roleService == null) {
            roleService = new RoleService();
        }
        return roleService;
    }

    public synchronized ISpeakerProposalService getSpeakerProposalService() {
        if (speakerProposalService == null) {
            speakerProposalService = new SpeakerProposalService();
        }
        return speakerProposalService;
    }

    public synchronized ISpeakerService getSpeakerService() {
        if (speakerService == null) {
            speakerService = new SpeakerService();
        }
        return speakerService;
    }

    public synchronized ITopicProposalService getTopicProposalService() {
        if (topicProposalService == null) {
            topicProposalService = new TopicProposalService();
        }
        return topicProposalService;
    }

    public synchronized IUserService getUserService() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }
}
