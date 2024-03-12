package com.cos.photogramstart.domain.folllow.infrastructure;

import com.cos.photogramstart.domain.folllow.application.FollowResult;

import java.util.List;

public interface FollowCustomRepository {

    int followState(Long principalId, int pageUserId);
    int followingCount(int pageUserId);
    int followerCount(int pageUserId);
    List<FollowResult> followingList(int principalId , int pageUser);
    List<FollowResult> followerList(Long principalId , Long pageUser);
}
