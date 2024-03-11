package com.cos.photogramstart.domain.folllow.application;

import com.cos.photogramstart.domain.folllow.exception.FollowMyselfFailException;
import com.cos.photogramstart.domain.folllow.exception.UnfollowFailException;
import com.cos.photogramstart.domain.folllow.exception.UnfollowMyselfFailException;
import com.cos.photogramstart.domain.folllow.infrastructure.FollowRepository;
import com.cos.photogramstart.domain.user.infrastructure.UserRepository;
import com.cos.photogramstart.global.error.exception.EntityAlreadyExistException;
import com.cos.photogramstart.global.util.AuthUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.cos.photogramstart.domain.user.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class FollowServiceTest {

    @InjectMocks
    private FollowService followService;

    @Mock
    private FollowRepository followRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthUtil authUtil;


    @DisplayName("자기 자신을 팔로우 할 수 없다. ")
    @Test
    void follow_Fail_FollowMyself() {
        // given
        given(authUtil.getLoginUser())
                .willReturn(PARK);

        given(userRepository.findByUsername("geonhee"))
                .willReturn(Optional.of(PARK));
        // when , then
        assertThatThrownBy(
                () -> followService.follow("geonhee")
        ).isInstanceOf(FollowMyselfFailException.class);
    }

    @DisplayName("이미 팔로우 한 유저를 팔로우 할 수 없다.")
    @Test
    void follow_Fail_AlreadyFollowing(){
        // given
        given(authUtil.getLoginUser())
                .willReturn(PARK);

        given(userRepository.findByUsername("cony"))
                .willReturn(Optional.of(CONY));

        given(followRepository.existsByFromUserIdAndToUserId(PARK.getId(),CONY.getId()))
                .willReturn(true);
        // when , then
        assertThatThrownBy(
                ()->    followService.follow("cony")
        ).isInstanceOf(EntityAlreadyExistException.class);
    }

    @DisplayName("자기 자신을 팔로우 취소 할 수 없다. ")
    @Test
    void unfollow_Fail_UnFollowMyself() {
        // given
        given(authUtil.getLoginUser())
                .willReturn(PARK);

        given(userRepository.findByUsername("geonhee"))
                .willReturn(Optional.of(PARK));
        // when , then
        assertThatThrownBy(
                () -> followService.unfollow("geonhee")
        ).isInstanceOf(UnfollowMyselfFailException.class);
    }

    @DisplayName("팔로우 하지 않은 유저를 팔로우 취소 할 수 없다. ")
    @Test
    void unfollow_Fail_NotFollowing() {
        // given
        given(authUtil.getLoginUser())
                .willReturn(PARK);

        given(userRepository.findByUsername("cony"))
                .willReturn(Optional.of(CONY));

        given(followRepository.findByFromUserIdAndToUserId(PARK.getId(), CONY.getId()))
                .willReturn(Optional.empty());
        // when , then
        assertThatThrownBy(
                () -> followService.unfollow("cony")
        ).isInstanceOf(UnfollowFailException.class);
    }

}