package com.cos.photogramstart.domain.folllow;

public interface FollowRepositoryCustom {

    int followState(int principalId, int pageUserId);
    int followingCount(int pageUserId);
    int followerCount(int pageUserId);

}
