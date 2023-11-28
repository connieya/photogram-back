package com.cos.photogramstart.domain.folllow.service;

import com.cos.photogramstart.domain.folllow.exception.FollowMyselfFailException;
import com.cos.photogramstart.domain.folllow.repository.FollowRepository;
import com.cos.photogramstart.domain.user.entity.User;
import com.cos.photogramstart.domain.user.repository.UserRepository;
import com.cos.photogramstart.global.error.ErrorCode;
import com.cos.photogramstart.global.error.exception.EntityAlreadyExistException;
import com.cos.photogramstart.global.error.exception.EntityNotFoundException;
import com.cos.photogramstart.global.util.AuthUtil;
import com.cos.photogramstart.handler.exception.CustomApiException;
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
    private final UserRepository userRepository;
    private final AuthUtil authUtil;
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
    public void follow(String username) {
        User loginUser = authUtil.getLoginUser();
        User followUser = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));


        if (loginUser.getUsername().equals(username)){
           throw new FollowMyselfFailException();
        }
        if (followRepository.existsByFromUserIAndToUserId(loginUser.getId(), followUser.getId())) {
            throw new EntityAlreadyExistException(ErrorCode.FOLLOW_ALREADY_EXIST);
        }

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
