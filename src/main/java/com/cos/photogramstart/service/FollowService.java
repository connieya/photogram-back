package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.folllow.FollowRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.web.dto.follow.FollowDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final EntityManager em;

    // 팔로잉 리스트
    @Transactional(readOnly = true)
    public List<FollowDto> followingList(int principalId, int pageUserId) {
        List<FollowDto> followingList = followRepository.followingList(principalId, pageUserId);
        return followingList;
    }

    // 팔로워 리스트
    @Transactional(readOnly = true)
    public List<FollowDto> followerList(int principalId, int pageUserId) {
        List<FollowDto> followerList = followRepository.followerList(principalId, pageUserId);
        return followerList;
    }


    @Transactional
    public void follow(int fromUserId, int toUserId) {
        try {
            followRepository.mFollow(fromUserId, toUserId);
        } catch (Exception e) {
            throw new CustomApiException("이미 구독을 하였습니다.");
        }

    }

    @Transactional
    public void unfollow(int fromUserId, int toUserId) {
        followRepository.mUnfollow(fromUserId, toUserId);
    }

}
