package com.cos.photogramstart.domain.comment.infrastructure;

import com.cos.photogramstart.domain.comment.application.CommentResult;
import com.cos.photogramstart.domain.comment.domain.Comment;
import com.cos.photogramstart.domain.post.application.PostUpload;
import com.cos.photogramstart.domain.post.domain.Post;
import com.cos.photogramstart.domain.post.domain.PostImage;
import com.cos.photogramstart.domain.post.infrastructure.PostImageRepository;
import com.cos.photogramstart.domain.post.infrastructure.PostRepository;
import com.cos.photogramstart.domain.user.application.command.SignUpCommand;
import com.cos.photogramstart.domain.user.domain.Image;
import com.cos.photogramstart.domain.user.domain.User;
import com.cos.photogramstart.domain.user.infrastructure.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentRepositoryImplTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostImageRepository postImageRepository;

    Post post;
    User geonhee;
    User cony;

    @BeforeEach
    void beforeEach() {
        geonhee = User.create(
                SignUpCommand
                        .builder()
                        .name("박건희")
                        .username("geonhee")
                        .email("geonhee@nate.com")
                        .password("1234")
                        .build()
        );

        cony = User.create(
                SignUpCommand
                        .builder()
                        .name("코니")
                        .username("cony")
                        .email("cony@nate.com")
                        .password("1234")
                        .build()
        );

        PostImage postImage = new PostImage(
                new Image(
                        "src/test"
                        , "JPG"
                        , "aaa-bbb-cc"
                        , "file"
                )
        );
        postImageRepository.save(postImage);
        post = Post.create(
                PostUpload
                        .builder()
                        .build()
                , geonhee
                , postImage
        );

        userRepository.saveAll(List.of(geonhee, cony));
        postRepository.save(post);
        commentRepository.saveAll(List.of(
                Comment.create("댓글 달기 1", post, geonhee)
                , Comment.create("안녕하세요", post, cony)

        ));
    }

    @DisplayName("댓글 조회 하기")
    @Test
    void findComment() {
        // given , when
        List<CommentResult> findComments = commentRepository.findByPostId(post.getId());
        //then
        assertThat(findComments).hasSize(2)
                .extracting("content", "userId", "username")
                .containsExactlyInAnyOrder(
                        tuple("댓글 달기 1", geonhee.getId(), "geonhee"),
                        tuple("안녕하세요", cony.getId(), "cony")
                );
    }

}