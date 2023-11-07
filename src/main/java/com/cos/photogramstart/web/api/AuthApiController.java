package com.cos.photogramstart.web.api;


import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.RespDto;
import com.cos.photogramstart.web.dto.auth.SignInRequest;
import com.cos.photogramstart.web.dto.auth.SignInResponse;
import com.cos.photogramstart.web.dto.auth.SignupDto;
import com.cos.photogramstart.web.dto.jwt.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class AuthApiController {

    private final AuthService authService;

    @PostMapping("/auth/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupDto signupDto , BindingResult bindingResult){
        User userEntity = authService.signup(signupDto.toEntity());
        return new ResponseEntity<>(new RespDto<>(1, "회원가입 성공", userEntity), HttpStatus.CREATED);
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<?> signin(@RequestBody SignInRequest signInRequest) {
        System.out.println("로그인 요청 ");
        return authService.signin(signInRequest);
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@RequestBody TokenDto tokenDto) {
        TokenDto reissue = authService.reissue(tokenDto);
        return new ResponseEntity<>(new RespDto<>(1, "refresh token 발급", reissue), HttpStatus.CREATED);
    }
}
