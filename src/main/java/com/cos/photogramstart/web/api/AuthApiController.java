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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class AuthApiController {

    private final AuthService authService;

    @PostMapping("/auth/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupDto signupDto , BindingResult bindingResult){
        System.out.println("signupDto = " + signupDto);
        System.out.println("bindingResult.hasError => " + bindingResult.hasErrors());
        if(bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for(FieldError error : bindingResult.getFieldErrors()) {
                System.out.println("error ======> " + error);
                errorMap.put(error.getField() , error.getDefaultMessage());
            }
            return new ResponseEntity<>(new RespDto<>(0, "회원가입 실패", errorMap), HttpStatus.BAD_REQUEST);
        }

        User user = signupDto.toEntity();
        User userEntity = authService.signup(user);
        return new ResponseEntity<>(new RespDto<>(1, "회원가입 성공", userEntity), HttpStatus.CREATED);
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<?> signin(@RequestBody SignInRequest signInRequest) {
        SignInResponse signInResponse = authService.signin(signInRequest);
        return new ResponseEntity<>(new RespDto<>(1, "로그인 성공", signInResponse), HttpStatus.OK);
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@RequestBody TokenDto tokenDto) {
        TokenDto reissue = authService.reissue(tokenDto);
        return new ResponseEntity<>(new RespDto<>(1, "refresh token 발급", reissue), HttpStatus.CREATED);
    }
}
