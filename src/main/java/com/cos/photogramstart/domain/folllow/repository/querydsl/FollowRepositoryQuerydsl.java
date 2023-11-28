package com.cos.photogramstart.domain.folllow.repository.querydsl;

import com.cos.photogramstart.web.dto.follow.FollowDto;

import java.util.List;

public interface FollowRepositoryQuerydsl {

    int followState(Long principalId, int pageUserId);
    int followingCount(int pageUserId);
    int followerCount(int pageUserId);
    List<FollowDto> followingList(int principalId , int pageUser);
    List<FollowDto> followerList(int principalId , int pageUser);
}
