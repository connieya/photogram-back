package com.cos.photogramstart.domain.user.infrastructure;


import com.cos.photogramstart.domain.user.application.command.SignUpCommand;
import com.cos.photogramstart.domain.user.domain.User;
import org.assertj.core.api.Assertions;
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
    }

}