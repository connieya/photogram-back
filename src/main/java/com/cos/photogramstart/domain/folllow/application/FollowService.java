package com.cos.photogramstart.domain.folllow.application;

import com.cos.photogramstart.domain.folllow.domain.Follow;
import com.cos.photogramstart.domain.folllow.exception.FollowMyselfFailException;
import com.cos.photogramstart.domain.folllow.exception.UnfollowFailException;
import com.cos.photogramstart.domain.folllow.exception.UnfollowMyselfFailException;
import com.cos.photogramstart.domain.folllow.infrastructure.FollowRepository;
import com.cos.photogramstart.domain.user.domain.User;
import com.cos.photogramstart.domain.user.infrastructure.UserRepository;
import com.cos.photogramstart.global.error.exception.EntityAlreadyExistException;
import com.cos.photogramstart.global.error.exception.EntityNotFoundException;
import com.cos.photogramstart.global.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.cos.photogramstart.global.error.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final AuthUtil authUtil;

    @Transactional(readOnly = true)
    public List<FollowResult> getFollowingResult(String username) {
        User loginUser = authUtil.getLoginUser();
        User pageUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        return followRepository.followingList(loginUser.getId(),pageUser.getId());
    }

    @Transactional(readOnly = true)
    public List<FollowResult> getFollowerResult(String username) {
        User loginUser = authUtil.getLoginUser();
        User pageUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        return followRepository.followerList(loginUser.getId(), pageUser.getId());
    }


    @Transactional
    public void follow(String username) {
        User loginUser = authUtil.getLoginUser();
        User followUser = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));

        if (loginUser.getUsername().equals(username)){
           throw new FollowMyselfFailException();
        }
        if (followRepository.existsByFromUserIdAndToUserId(loginUser.getId(), followUser.getId())) {
            throw new EntityAlreadyExistException(FOLLOW_ALREADY_EXIST);
        }
        Follow follow = new Follow(loginUser, followUser);
        followRepository.save(follow);

    }

    @Transactional
    public void unfollow(String username) {
        User loginUser = authUtil.getLoginUser();
        User followUser = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));

        if (loginUser.getUsername().equals(username)){
            throw new UnfollowMyselfFailException();
        }

        Follow follow = followRepository.findByFromUserIdAndToUserId(loginUser.getId(), followUser.getId())
                .orElseThrow(()-> new UnfollowFailException(UNFOLLOW_FAIL));
        followRepository.delete(follow);
    }


}
