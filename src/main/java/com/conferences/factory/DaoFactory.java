package com.conferences.factory;

import com.conferences.dao.abstraction.*;
import com.conferences.dao.implementation.*;

public class DaoFactory {

    private static DaoFactory instance;

    private IMeetingDao meetingDao;
    private IModeratorProposalDao moderatorProposalDao;
    private IProposalDataDao proposalDataDao;
    private IProposedTopicDataDao proposedTopicDataDao;
    private IReportTopicDao reportTopicDao;
    private IReportTopicSpeakerDao reportTopicSpeakerDao;
    private IRoleDao roleDao;
    private ISpeakerProposalDao speakerProposalDao;
    private ITopicProposalDao topicProposalDao;
    private IUserDao userDao;
    private IUserMeetingDao userMeetingDao;

    private DaoFactory() {}

    public static synchronized DaoFactory getInstance() {
        if (instance == null) {
            instance = new DaoFactory();
        }
        return instance;
    }

    public synchronized IMeetingDao getMeetingDao() {
        if (meetingDao == null) {
            meetingDao = new MeetingDao();
        }
        return meetingDao;
    }

    public synchronized IModeratorProposalDao getModeratorProposalDao() {
        if (moderatorProposalDao == null) {
            moderatorProposalDao = new ModeratorProposalDao();
        }
        return moderatorProposalDao;
    }

    public synchronized IProposalDataDao getProposalDataDao() {
        if (proposalDataDao == null) {
            proposalDataDao = new ProposalDataDao();
        }
        return proposalDataDao;
    }

    public synchronized IProposedTopicDataDao getProposedTopicDataDao() {
        if (proposedTopicDataDao == null) {
            proposedTopicDataDao = new ProposedTopicDataDao();
        }
        return proposedTopicDataDao;
    }

    public synchronized IReportTopicDao getReportTopicDao() {
        if (reportTopicDao == null) {
            reportTopicDao = new ReportTopicDao();
        }
        return reportTopicDao;
    }

    public synchronized IReportTopicSpeakerDao getReportTopicSpeakerDao() {
        if (reportTopicSpeakerDao == null) {
            reportTopicSpeakerDao = new ReportTopicSpeakerDao();
        }
        return reportTopicSpeakerDao;
    }

    public synchronized IRoleDao getRoleDao() {
        if (roleDao == null) {
            roleDao = new RoleDao();
        }
        return roleDao;
    }

    public synchronized ISpeakerProposalDao getSpeakerProposalDao() {
        if (speakerProposalDao == null) {
            speakerProposalDao = new SpeakerProposalDao();
        }
        return speakerProposalDao;
    }

    public synchronized ITopicProposalDao getTopicProposalDao() {
        if (topicProposalDao == null) {
            topicProposalDao = new TopicProposalDao();
        }
        return topicProposalDao;
    }

    public synchronized IUserDao getUserDao() {
        if (userDao == null) {
            userDao = new UserDao();
        }
        return userDao;
    }

    public synchronized IUserMeetingDao getUserMeetingDao() {
        if (userMeetingDao == null) {
            userMeetingDao = new UserMeetingDao();
        }
        return userMeetingDao;
    }
}
