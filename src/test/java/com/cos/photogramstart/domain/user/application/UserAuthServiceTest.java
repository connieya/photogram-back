package com.cos.photogramstart.domain.user.application;

import com.cos.photogramstart.domain.user.application.command.SignUpCommand;
import com.cos.photogramstart.domain.user.infrastructure.UserRepository;
import com.cos.photogramstart.global.error.exception.EntityAlreadyExistException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserAuthServiceTest {

    @InjectMocks
    private UserAuthService userAuthService;

    @Mock
    private UserRepository userRepository;


    @DisplayName("회원 가입 시 이미 사용 중인 유저 네임이 있을 경우 예외가 발생한다.")
    @Test
    void signup_DuplicateUsername() {
        // given
        given(userRepository.existsByUsername("gunhee"))
                .willReturn(true);
        SignUpCommand signUpCommand = SignUpCommand
                .builder()
                .username("gunhee")
                .email("gunny6026@naver.com")
                .name("박건희")
                .password("1234")
                .build();
        // when , then
        assertThatThrownBy(
                () ->
                        userAuthService.signup(signUpCommand)
        ).isInstanceOf(EntityAlreadyExistException.class);
    }


    @DisplayName("회원 가입 시 이미 사용 중인 이메일이 있을 경우 예외가 발생한다.")
    @Test
    void signup_DuplicateEmail() {
        // given
        given(userRepository.existsByEmail("gunny6026@naver.com"))
                .willReturn(true);
        SignUpCommand signUpCommand = SignUpCommand
                .builder()
                .username("gunhee")
                .email("gunny6026@naver.com")
                .name("박건희")
                .password("1234")
                .build();
        // when , then
        assertThatThrownBy(
                () ->
                        userAuthService.signup(signUpCommand)
        ).isInstanceOf(EntityAlreadyExistException.class);


    }
}