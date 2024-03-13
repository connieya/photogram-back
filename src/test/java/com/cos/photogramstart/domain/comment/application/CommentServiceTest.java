package com.cos.photogramstart.domain.comment.application;

import com.cos.photogramstart.domain.post.infrastructure.PostRepository;
import com.cos.photogramstart.global.error.exception.EntityNotFoundException;
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
class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private AuthUtil authUtil;

    @DisplayName("댓글 작성할 때 해당 게시물이 없으면 예외가 발생한다.")
    @Test
    void write_fail(){
        // given
        given(authUtil.getLoginUser())
                .willReturn(PARK);

        CommentCommand commentCommand = new CommentCommand(1L, "댓글 달기");
        given(postRepository.findById(commentCommand.getImageId()))
                .willReturn(Optional.empty());
        // when , then
        assertThatThrownBy( ()->
                commentService.write(commentCommand)
        ).isInstanceOf(EntityNotFoundException.class);
    }

}