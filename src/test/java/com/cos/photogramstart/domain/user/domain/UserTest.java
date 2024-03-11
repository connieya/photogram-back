package com.cos.photogramstart.domain.user.domain;


import com.cos.photogramstart.domain.user.application.command.SignUpCommand;
import com.cos.photogramstart.domain.user.application.command.UserUpdateCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class UserTest {

    @DisplayName("SignUpCommand 로 User 객체를 생성할 수 있다.")
    @Test
    void create(){
        // given
        SignUpCommand signUpCommand = SignUpCommand
                .builder()
                .username("cony")
                .password("1234")
                .name("박건희")
                .email("cony@nate.com")
                .build();
        // when
        User user = User.create(signUpCommand);

        //then
        assertThat(user.getEmail()).isEqualTo("cony@nate.com");
        assertThat(user.getUsername()).isEqualTo("cony");
        assertThat(user.getName()).isEqualTo("박건희");
        assertThat(user.getPassword()).isEqualTo("1234");
    }

    @DisplayName("회원 정보를 수정 한다.")
    @Test
    void update(){
        // given
        User user = User.create(1L, "cony", "1234", "코니", "cony@nate.com");
        // when
        UserUpdateCommand userUpdateCommand = UserUpdateCommand
                .builder()
                .name("건희")
                .username("gunny")
                .website("https://github.com/connieya")
                .bio("백엔드 개발자 입니다.")
                .build();
        user.update(userUpdateCommand);
        //then
        assertThat(user.getName()).isEqualTo("건희");
        assertThat(user.getUsername()).isEqualTo("gunny");
        assertThat(user.getWebsite()).isEqualTo("https://github.com/connieya");
        assertThat(user.getBio()).isEqualTo("백엔드 개발자 입니다.");
    }

}