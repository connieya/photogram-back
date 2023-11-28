package com.cos.photogramstart.web.api;


import com.cos.photogramstart.global.response.ResponseEnum;
import com.cos.photogramstart.global.response.SuccessResponse;
import com.cos.photogramstart.domain.user.entity.User;
import com.cos.photogramstart.domain.user.service.UserAuthService;
import com.cos.photogramstart.web.dto.RespDto;
import com.cos.photogramstart.web.dto.auth.SignInRequest;
import com.cos.photogramstart.web.dto.auth.SignInResponse;
import com.cos.photogramstart.web.dto.auth.SignupDto;
import com.cos.photogramstart.web.dto.jwt.TokenDto;
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
@RestController
@Slf4j
public class AuthController {

    private final UserAuthService authService;

    @PostMapping("/auth/signup")
    public SuccessResponse<User> signup(@Valid @RequestBody SignupDto signupDto, BindingResult bindingResult) {
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
