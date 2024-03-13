package com.cos.photogramstart.domain.user.infrastructure;

import com.cos.photogramstart.domain.user.application.command.SignUpCommand;
import com.cos.photogramstart.domain.user.application.result.UserProfileResult;
import com.cos.photogramstart.domain.user.domain.Image;
import com.cos.photogramstart.domain.user.domain.User;
import com.cos.photogramstart.global.config.QuerydslConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestConstructor;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({ QuerydslConfig.class})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class UserRepositoryTest {


    @Autowired
    UserRepository userRepository;
    User geonhee;
    User cony;

    @BeforeEach
    void beforeEach() {
        geonhee = User
                .create(
                        SignUpCommand
                                .builder()
                                .name("박건희")
                                .username("geonhee")
                                .password("1234")
                                .email("geonhee@nate.com")
                                .build()
                );
        cony = User
                .create(
                        SignUpCommand
                                .builder()
                                .name("코니")
                                .username("cony")
                                .password("1234")
                                .email("cony@nate.com")
                                .build()
                );
        userRepository.saveAll(List.of(geonhee, cony));

    }

    @DisplayName("회원을 등록한다.")
    @Test
    void create(){
        // given
        Image image = geonhee.getImage();
        // when ,then
        assertThat(image.getImageUUID()).isEqualTo("base-UUID");
        assertThat(image.getImageUrl()).isEqualTo("base-url");



    }


    @DisplayName("유저 네임으로 해당 프로필을 조회한다.")
    @Test
    void findUserProfile() {
        // given
        String username = "geonhee";
        // when
        UserProfileResult userProfile = userRepository.findUserProfile(geonhee, username);
        //then
        assertThat(userProfile.getName()).isEqualTo("박건희");
        assertThat(userProfile.getUsername()).isEqualTo("geonhee");
        assertThat(userProfile.isPageOwner()).isTrue();
    }

}