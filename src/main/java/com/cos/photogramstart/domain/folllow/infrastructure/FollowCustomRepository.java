package com.cos.photogramstart.domain.folllow.infrastructure;

import com.cos.photogramstart.domain.folllow.application.FollowResult;

import java.util.List;

public interface FollowCustomRepository {


    Long followingCount(Long pageUserId);
    Long followerCount(Long pageUserId);
    List<FollowResult> followingList(Long loginUserId , Long pageUser);
    List<FollowResult> followerList(Long loginUserId , Long pageUser);
}
