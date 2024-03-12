package com.cos.photogramstart.domain.post.application;

import com.cos.photogramstart.domain.post.fixture.PostFixture;
import com.cos.photogramstart.domain.post.infrastructure.PostLikeRepository;
import com.cos.photogramstart.domain.post.infrastructure.PostRepository;
import com.cos.photogramstart.domain.user.fixture.UserFixture;
import com.cos.photogramstart.global.error.exception.EntityAlreadyExistException;
import com.cos.photogramstart.global.error.exception.EntityNotFoundException;
import com.cos.photogramstart.global.util.AuthUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.cos.photogramstart.domain.post.fixture.PostFixture.*;
import static com.cos.photogramstart.domain.user.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PostLikeServiceTest {

    @InjectMocks
    private PostLikeService postLikeService;

    @Mock
    private PostLikeRepository postLikeRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private AuthUtil authUtil;

    @DisplayName("게시물에 좋아요를 추가하려고 할 때, 해당 게시물이 존재하지 않으면 예외가 발생한다")
    @Test
    void like_nonExistPost(){
        // given
        given(postRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        // when , then
        assertThatThrownBy(
                ()-> postLikeService.like(1L)
        ).isInstanceOf(EntityNotFoundException.class);
    }

    @DisplayName("게시물에 이미 좋아요를 누른 경우 예외가 발생한다")
    @Test
    void addLikeToAlreadyLikedPost_throwsException() {
        // given
        given(authUtil.getLoginUser())
                .willReturn((PARK));

        given(postRepository.findById(1L))
                .willReturn(Optional.of(POST_1));

        given(postLikeRepository.existsByPostIdAndUserId(1L, PARK.getId()))
                .willReturn(true);

        // when , then
        assertThatThrownBy(
                ()-> postLikeService.like(1L)
        ).isInstanceOf(EntityAlreadyExistException.class);
    }

}