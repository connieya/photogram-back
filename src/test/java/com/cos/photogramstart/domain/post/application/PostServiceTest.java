package com.cos.photogramstart.domain.post.application;

import com.cos.photogramstart.domain.comment.application.CommentResult;
import com.cos.photogramstart.domain.post.exception.PostDeleteException;
import com.cos.photogramstart.domain.post.fixture.PostFixture;
import com.cos.photogramstart.domain.post.infrastructure.PostRepository;
import com.cos.photogramstart.domain.user.fixture.UserFixture;
import com.cos.photogramstart.global.util.AuthUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.cos.photogramstart.domain.post.fixture.PostFixture.*;
import static com.cos.photogramstart.domain.user.fixture.UserFixture.*;
import static com.cos.photogramstart.domain.user.fixture.UserFixture.PARK;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private AuthUtil authUtil;

    @DisplayName("게시물을 조회한다.")
    @Test
    void findPost(){
        // given
        given(postRepository.findById(1L))
                .willReturn(Optional.of(POST_1));
        // when
        PostDetail postDetail = postService.findPost(1L);
        List<CommentResult> commentResults = postDetail.getCommentResults();
        //then
        assertThat(postDetail.getPostId()).isEqualTo(1L);
        assertThat(postDetail.getCaption()).isEqualTo("게시물 1");
        assertThat(postDetail.getLocation()).isEqualTo("서울시 송파구");
        assertThat(postDetail.getPostImageUrl()).isEqualTo("base-url");
        assertThat(postDetail.getUsername()).isEqualTo("geonhee");
        assertThat(commentResults).hasSize(2)
                .extracting("commentId","content","userId","username")
                .containsExactlyInAnyOrder(
                        tuple(1,"댓글 달기 1", CONY.getId(),"cony"),
                        tuple(2,"댓글 달기 2", HONG.getId(),"hong")
                );

    }

    @DisplayName("다른 사람이 작성한 게시물은 삭제 할 수 없다.")
    @Test
    void delete_exception(){
        // given
        given(authUtil.getLoginUser())
                .willReturn(HONG);

        given(postRepository.findById(1L))
                .willReturn(Optional.of(POST_1));
        // when , then
        assertThatThrownBy(
                ()-> postService.delete(1L)
                ).isInstanceOf(PostDeleteException.class);

    }

}