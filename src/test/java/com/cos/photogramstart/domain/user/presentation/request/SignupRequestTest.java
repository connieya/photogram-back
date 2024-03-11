package com.cos.photogramstart.domain.user.presentation.request;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class SignupRequestTest {

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    @DisplayName("회원 가입 유효성 검사 -  이름은 필수 항목 이다.")
    @Test
    void validateNameRequired(){
        // given
        SignupRequest signupRequest = SignupRequest
                .builder()
                .password("1122332")
                .username("cony")
                .email("cony@nate.com")
                .build();
        // when
        List<String> messages = new ArrayList<>();
        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);
        for (ConstraintViolation<SignupRequest> violation : violations) {
            messages.add(violation.getMessage());
        }
        //then
        assertThat(messages).hasSize(1)
                .contains(
                        "이름은 필수 입니다."
                );
    }


    @DisplayName("회원 가입 유효성 검사 -  이름은 한글 또는 영문만 입력 할 수 있다.")
    @Test
    void validateNameOnlyKoreanOrEnglish(){
        // given
        SignupRequest signupRequest = SignupRequest
                .builder()
                .name("안dd")
                .password("1122332")
                .username("cony")
                .email("cony@nate.com")
                .build();
        // when
        boolean result = signupRequest.validateName();
        // then
        assertThat(result).isFalse();
    }


    @DisplayName("회원 가입 유효성 검사 -  이름은 한글 또는 영문만 입력 할 수 있다.")
    @Test
    void validateNameOnlyKoreanOrEnglish2(){
        // given
        SignupRequest signupRequest = SignupRequest
                .builder()
                .name("d22")
                .password("1122332")
                .username("cony")
                .email("cony@nate.com")
                .build();
        // when
        boolean result = signupRequest.validateName();
        // then
        assertThat(result).isFalse();
    }



    @DisplayName("회원 가입 유효성 검사 -  이름은 한글 또는 영문만 입력 할 수 있다.")
    @Test
    void validateNameOnlyKoreanOrEnglish3(){
        // given
        SignupRequest signupRequest = SignupRequest
                .builder()
                .name("안 녕")
                .password("1122332")
                .username("cony")
                .email("cony@nate.com")
                .build();
        // when
        boolean result = signupRequest.validateName();
        // then
        assertThat(result).isTrue();
    }


    @DisplayName("회원 가입 유효성 검사 -  이름은 한글 또는 영문만 입력 할 수 있다.")
    @Test
    void validateNameOnlyKoreanOrEnglish4(){
        // given
        SignupRequest signupRequest = SignupRequest
                .builder()
                .name("안 녕")
                .password("1122332")
                .username("cony")
                .email("cony@nate.com")
                .build();
        // when
        boolean result = signupRequest.validateName();
        // then
        assertThat(result).isTrue();
    }


    @DisplayName("회원 가입 유효성 검사 -  이름은 한글 또는 영문만 입력 할 수 있다.")
    @Test
    void validateNameOnlyKoreanOrEnglish5(){
        // given
        SignupRequest signupRequest = SignupRequest
                .builder()
                .name("mike dd")
                .password("1122332")
                .username("cony")
                .email("cony@nate.com")
                .build();
        // when
        boolean result = signupRequest.validateName();
        // then
        assertThat(result).isTrue();
    }


    @DisplayName("회원 가입 유효성 검사 -  올바른 이메일 형식을 입력 해야 한다.")
    @Test
    void validateCorrectEmailFormat(){
        // given
        SignupRequest signupRequest = SignupRequest
                .builder()
                .name("박건희")
                .password("1122332")
                .username("cony")
                .email("cony")
                .build();
        // when
        List<String> messages = new ArrayList<>();
        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);
        for (ConstraintViolation<SignupRequest> violation : violations) {
            messages.add(violation.getMessage());
        }
        //then
        assertThat(messages).hasSize(1)
                .contains(
                        "올바른 이메일 주소 형식이 아닙니다."
                );
    }

    @DisplayName("회원 가입 유효성 검사 -  올바른 이메일 형식을 입력 해야 한다.")
    @Test
    void validateCorrectEmailFormat2(){
        // given
        SignupRequest signupRequest = SignupRequest
                .builder()
                .name("박건희")
                .password("1122332")
                .username("cony")
                .email("cony@")
                .build();
        // when
        List<String> messages = new ArrayList<>();
        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);
        for (ConstraintViolation<SignupRequest> violation : violations) {
            messages.add(violation.getMessage());
        }
        //then
        assertThat(messages).hasSize(1)
                .contains(
                        "올바른 이메일 주소 형식이 아닙니다."
                );
    }


    @DisplayName("회원 가입 유효성 검사 -  올바른 이메일 형식을 입력 해야 한다.")
    @Test
    void validateCorrectEmailFormat3(){
        // given
        SignupRequest signupRequest = SignupRequest
                .builder()
                .name("박건희")
                .password("1122332")
                .username("cony")
                .email("cony@naver")
                .build();
        // when
        List<String> messages = new ArrayList<>();
        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);
        for (ConstraintViolation<SignupRequest> violation : violations) {
            messages.add(violation.getMessage());
        }
        //then
        assertThat(messages).hasSize(1)
                .contains(
                        "올바른 이메일 주소 형식이 아닙니다."
                );
    }

    @DisplayName("회원 가입 유효성 검사 -  유저 네임은 영문만 입력 가능 하다.")
    @Test
    void validateUsernameOnlyEnglish(){
        // given
        SignupRequest signupRequest = SignupRequest
                .builder()
                .name("박건희")
                .password("1122332")
                .username("코니")
                .email("cony@naver.com")
                .build();
        // when
        List<String> messages = new ArrayList<>();
        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);
        for (ConstraintViolation<SignupRequest> violation : violations) {
            messages.add(violation.getMessage());
        }
        //then
        assertThat(messages).hasSize(1)
                .contains(
                        "유저 네임은 영문으로만 입력 가능 합니다."
                );
    }


    @DisplayName("회원 가입 유효성 검사 -  유저 네임은 영문만 입력 가능 하다.")
    @Test
    void validateUsernameOnlyEnglish2(){
        // given
        SignupRequest signupRequest = SignupRequest
                .builder()
                .name("박건희")
                .password("1122332")
                .username("co ny")
                .email("cony@naver.com")
                .build();
        // when
        List<String> messages = new ArrayList<>();
        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);
        for (ConstraintViolation<SignupRequest> violation : violations) {
            messages.add(violation.getMessage());
        }
        //then
        assertThat(messages).hasSize(1)
                .contains(
                        "유저 네임은 영문으로만 입력 가능 합니다."
                );
    }
}