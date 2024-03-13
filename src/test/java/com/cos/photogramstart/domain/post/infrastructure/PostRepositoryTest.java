package com.cos.photogramstart.domain.post.infrastructure;

import com.cos.photogramstart.domain.folllow.domain.Follow;
import com.cos.photogramstart.domain.folllow.infrastructure.FollowRepository;
import com.cos.photogramstart.domain.post.application.PostResult;
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

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class PostRepositoryTest {


    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostLikeRepository postLikeRepository;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;
    User admin;
    User park;
    User kim;
    User lee;
    User moon;

    @BeforeEach
    void beforeEach() {
        admin = User.create(
                SignUpCommand
                        .builder()
                        .name("admin")
                        .username("admin")
                        .email("admin@nate.com")
                        .password("1234")
                        .build()
        );

        park = User.create(
                SignUpCommand
                        .builder()
                        .name("park")
                        .username("park")
                        .email("park@nate.com")
                        .password("1234")
                        .build()
        );

        kim = User.create(
                SignUpCommand
                        .builder()
                        .name("kim")
                        .username("kim")
                        .email("kim@nate.com")
                        .password("1234")
                        .build()
        );

        lee = User.create(
                SignUpCommand
                        .builder()
                        .name("lee")
                        .username("lee")
                        .email("lee@nate.com")
                        .password("1234")
                        .build()
        );

        moon = User.create(
                SignUpCommand
                        .builder()
                        .name("moon")
                        .username("moon")
                        .email("moon@nate.com")
                        .password("1234")
                        .build()
        );

        userRepository.saveAll(List.of(admin, park, kim, lee, moon));
        followRepository.save(new Follow(park, kim));
        followRepository.save(new Follow(lee, kim));
        followRepository.save(new Follow(lee, park));
        followRepository.save(new Follow(lee, admin));
        followRepository.save(new Follow(moon, kim));
        followRepository.save(new Follow(admin, lee));
        followRepository.save(new Follow(admin, park));
        followRepository.save(new Follow(admin, kim));
        Image image = new Image(
                "src/test"
                , "JPG"
                , "BASE-UUID"
                , "FIlE"
        );
        PostImage postImage = new PostImage(image);
        Post post1 = Post.create(
                PostUpload
                        .builder()
                        .caption("게시물 작성")
                        .location("서울시 송파구")
                        .build()
                , kim
                , postImage
        );
        Post post2 = Post.create(
                PostUpload
                        .builder()
                        .caption("날씨 좋다.")
                        .location("부산광역시")
                        .build()
                , park
                , postImage
        );
        Post post3 = Post.create(
                PostUpload
                        .builder()
                        .caption("여행 가고 싶다.")
                        .location("서울")
                        .build()
                , moon
                , postImage
        );
        Post post4 = Post.create(
                PostUpload
                        .builder()
                        .caption("팔로워 10000명 돌파")
                        .location("우리 집")
                        .build()
                , lee
                , postImage
        );
        postRepository.saveAll(
                List.of(
                        post1
                        , post2
                        , post3
                        , post4
                )
        );

        PostLike postLike = new PostLike(post1, admin);
        postLikeRepository.save(postLike);

    }

    @DisplayName("팔로우 한 회원의 모든 게시물 조회")
    @Test
    void findAll() {
        // given
        List<PostResult> postResultList = postRepository.findAll(admin.getId());
        // when , then
        assertThat(postResultList).hasSize(3)
                .extracting("postImageUrl", "content", "userId", "username", "likeState")
                .containsExactlyInAnyOrder(
                        tuple("src/test", "팔로워 10000명 돌파", lee.getId(), "lee", false),
                        tuple("src/test", "날씨 좋다.", park.getId(), "park", false),
                        tuple("src/test", "게시물 작성", kim.getId(), "kim", true)
                );


    }

}