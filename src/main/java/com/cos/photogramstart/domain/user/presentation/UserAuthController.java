package com.cos.photogramstart.domain.user.presentation;


import com.cos.photogramstart.domain.user.application.UserAuthService;
import com.cos.photogramstart.domain.user.application.result.SignInResult;
import com.cos.photogramstart.global.result.ResultResponse;
import com.cos.photogramstart.domain.user.presentation.request.SignInRequest;
import com.cos.photogramstart.domain.user.presentation.response.SignInResponse;
import com.cos.photogramstart.domain.user.presentation.request.SignupRequest;
import com.cos.photogramstart.global.config.security.TokenDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.cos.photogramstart.global.result.ResultCode.*;

@RequiredArgsConstructor
@Api(tags = "멤버 인증 API")
@RestController
@Slf4j
public class UserAuthController {

    private final UserAuthService authService;

    @ApiOperation(value = "회원가입")
    @PostMapping("/auth/signup")
    public ResponseEntity<ResultResponse> signup(@Valid @RequestBody SignupRequest signupRequest) {
        log.info("회원 가입 요청");
        authService.signup(signupRequest.toCommand());
        return ResponseEntity.ok(ResultResponse.of(REGISTER_SUCCESS));
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/auth/signin")
    public ResponseEntity<ResultResponse> signin(@RequestBody SignInRequest signInRequest) {
       SignInResult result = authService.signin(signInRequest);
        return ResponseEntity.ok(ResultResponse.of(LOGIN_SUCCESS,SignInResponse.from(result)));
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@RequestBody TokenDto tokenDto) {
        TokenDto reissue = authService.reissue(tokenDto);
        return ResponseEntity.ok(ResultResponse.of(REISSUE_SUCCESS,reissue));
    }

}
