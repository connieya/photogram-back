package com.cos.photogramstart.domain.post.infrastructure;

import com.cos.photogramstart.domain.post.application.PostUpload;
import com.cos.photogramstart.domain.post.domain.Post;
import com.cos.photogramstart.domain.post.domain.PostImage;
import com.cos.photogramstart.domain.post.domain.PostLike;
import com.cos.photogramstart.domain.user.application.command.SignUpCommand;
import com.cos.photogramstart.domain.user.domain.Image;
import com.cos.photogramstart.domain.user.domain.User;
import com.cos.photogramstart.domain.user.infrastructure.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostLikeRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostLikeRepository postLikeRepository;

    @Autowired
    private PostImageRepository postImageRepository;

    @Autowired
    private PostRepository postRepository;

    User cony;
    Post post1;

    @BeforeEach
    void beforeEach() {
        cony = User.create(
                SignUpCommand
                        .builder()
                        .username("cony")
                        .email("cony@nate.com")
                        .password("1234")
                        .name("코니")
                        .build()
        );
        userRepository.save(cony);
        PostImage postImage = new PostImage(
                new Image(
                        "src/test/resources"
                        , "JPG"
                        , "aaa-bbb"
                        , "file"
                ));

        postImageRepository.save(postImage);
        post1 = Post.create(
                PostUpload
                        .builder()
                        .caption("게시물 1")
                        .location("서울시")
                        .build()
                , cony
                , postImage
                );

        postRepository.save(post1);
        PostLike postLike = new PostLike(post1, cony);
        postLikeRepository.save(postLike);
    }

    @DisplayName("게시물 ID 와 유저 ID 로 게시글 좋아요 를 조회할 수 있다.")
    @Test
    void findByPostIdAndUserId() {
        // given
        PostLike postLike = postLikeRepository.findByPostIdAndUserId(post1.getId(), cony.getId());
        // when , then
        Post findPost = postLike.getPost();
        User findUser = postLike.getUser();
        assertThat(findPost.getCaption()).isEqualTo("게시물 1");
        assertThat(findPost.getLocation()).isEqualTo("서울시");
        assertThat(findPost.getUser()).isEqualTo(cony);
        assertThat(findUser.getName()).isEqualTo("코니");
        assertThat(findUser.getEmail()).isEqualTo("cony@nate.com");
        assertThat(findUser.getUsername()).isEqualTo("cony");
    }

    @DisplayName("게시물 id 와 유저 id 로 게시물 좋아요 눌렀는지 확인")
    @Test
    void existsByPostIdAndUserId(){
        // given
        boolean result = postLikeRepository.existsByPostIdAndUserId(post1.getId(), cony.getId());
        // when , then
        assertThat(result).isTrue();
    }


}