package com.conferences.model;

import com.conferences.entity.UserMeeting;

import java.util.List;

public class MeetingUsersStats {

    private long presentUsersCount;

    private List<UserMeeting> usersMeetings;

    public MeetingUsersStats(List<UserMeeting> usersMeetings) {
        presentUsersCount = -1;
        setUsersMeetings(usersMeetings);
    }

    public long getTotalUsersCount() {
        return usersMeetings.size();
    }

    public long getPresentUsersCount() {
        if (presentUsersCount == -1) {
            presentUsersCount = usersMeetings.stream()
                .filter(UserMeeting::isPresent)
                .count();
        }
        return presentUsersCount;
    }

    public List<UserMeeting> getUsersMeetings() {
        return usersMeetings;
    }

    public void setUsersMeetings(List<UserMeeting> usersMeetings) {
        presentUsersCount = -1;
        this.usersMeetings = usersMeetings;
    }
}
