package com.conferences.dao.abstraction;

import com.conferences.entity.User;

import java.util.List;

public interface IUserDao extends ICrudDao<Integer, User> {

    User findByLoginWithRole(String login, String password);

    User findByLoginOrEmail(String login, String email);

    List<User> findAllByRole(String role);

    List<User> findAvailableSpeakersForProposalByTopic(int topicId);

    boolean updateUserImagePath(User user);
}
