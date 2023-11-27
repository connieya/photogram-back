package com.cos.photogramstart.domain.folllow.repository;

import com.cos.photogramstart.web.dto.follow.FollowDto;

import java.util.List;

public interface FollowRepositoryCustom {

    int followState(int principalId, int pageUserId);
    int followingCount(int pageUserId);
    int followerCount(int pageUserId);
    List<FollowDto> followingList(int principalId , int pageUser);
    List<FollowDto> followerList(int principalId , int pageUser);
}
