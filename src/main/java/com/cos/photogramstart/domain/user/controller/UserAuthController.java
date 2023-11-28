package com.cos.photogramstart.domain.user.controller;


import com.cos.photogramstart.global.response.ResponseEnum;
import com.cos.photogramstart.global.response.SuccessResponse;
import com.cos.photogramstart.domain.user.entity.User;
import com.cos.photogramstart.domain.user.service.UserAuthService;
import com.cos.photogramstart.web.dto.RespDto;
import com.cos.photogramstart.domain.user.dto.SignInRequest;
import com.cos.photogramstart.domain.user.dto.SignInResponse;
import com.cos.photogramstart.domain.user.dto.SignupRequest;
import com.cos.photogramstart.web.dto.jwt.TokenDto;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@Api(tags = "멤버 인증 API")
@RestController
@Slf4j
public class UserAuthController {

    private final UserAuthService authService;

    @PostMapping("/auth/signup")
    public SuccessResponse<User> signup(@Valid @RequestBody SignupRequest signupDto, BindingResult bindingResult) {
        log.info("회원 가입 = {} ", signupDto);
        authService.signup(signupDto.toEntity());
        return new SuccessResponse<>(ResponseEnum.SIGNUP_SUCCESS);
    }

    @PostMapping("/auth/signin")
    public SuccessResponse<?> signin(@RequestBody SignInRequest signInRequest) {
        SignInResponse response = authService.signin(signInRequest);
        return new SuccessResponse<>(ResponseEnum.SIGNIN_SUCCESS, response);
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@RequestBody TokenDto tokenDto) {
        TokenDto reissue = authService.reissue(tokenDto);
        return new ResponseEntity<>(new RespDto<>(1, "refresh token 발급", reissue), HttpStatus.CREATED);
    }

    @PostMapping("/auth/password")
    public void changePassword(@RequestBody SignInRequest signInRequest) {
        authService.changePassword(signInRequest);
    }
}
