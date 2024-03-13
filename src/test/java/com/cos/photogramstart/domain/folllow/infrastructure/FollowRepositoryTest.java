package com.cos.photogramstart.domain.folllow.infrastructure;

import com.cos.photogramstart.domain.folllow.application.FollowResult;
import com.cos.photogramstart.domain.folllow.domain.Follow;
import com.cos.photogramstart.domain.user.application.command.SignUpCommand;
import com.cos.photogramstart.domain.user.domain.User;
import com.cos.photogramstart.domain.user.infrastructure.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class FollowRepositoryTest {

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
    }

    @DisplayName("팔로워 목록을 조회 한다. ")
    @Test
    void followerList() {
        // given
        List<FollowResult> followResults = followRepository.followerList(admin.getId(), kim.getId());
        // when , then
        assertThat(followResults).hasSize(4)
                .extracting("username", "followState", "isCurrentUser")
                .containsExactlyInAnyOrder(
                        tuple("park", true, false),
                        tuple("lee", true, false),
                        tuple("moon", false, false),
                        tuple("admin", false, true)
                );
        ;
    }

    @DisplayName("팔로잉 목록을 조회 한다.")
    @Test
    void followingList(){
        // given
        List<FollowResult> followResults = followRepository.followingList(admin.getId(), lee.getId());
        // when , then
        assertThat(followResults).hasSize(3)
                .extracting("username", "followState", "isCurrentUser")
                .containsExactlyInAnyOrder(
                        tuple("park", true, false),
                        tuple("kim", true, false),
                        tuple("admin", false, true)
                );
    }

    @DisplayName("팔로잉 하는 숫자")
    @Test
    void followingCount(){
        // given   when
        Long followingCount = followRepository.followingCount(admin.getId());
        //then
        assertThat(followingCount).isEqualTo(3);
    }

    @DisplayName("팔로워 숫자")
    @Test
    void followerCount(){
        // given ,when
        Long followerCount = followRepository.followerCount(admin.getId());
        //then
        assertThat(followerCount).isEqualTo(1);
    }

}