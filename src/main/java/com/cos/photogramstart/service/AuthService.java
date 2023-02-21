package com.cos.photogramstart.service;

import com.cos.photogramstart.config.jwt.JWTTokenHelper;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.web.dto.auth.SignInRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service // Ioc , 트랜잭션관리
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManager;
    private final JWTTokenHelper tokenHelper;

    // 회원가입
    @Transactional // insert , update ,delete 시 사용
    public User signup(User user){
        if(userRepository.existsByUsername(user.getUsername())){
            throw new CustomApiException("사용중인 아이디 입니다.");
        }
        if(userRepository.existsByEmail(user.getEmail())){
            throw new CustomApiException("사용중인 이메일 입니다.");
        }
        String rawPassword = user.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        user.setRole("ROLE_USER");
        User userEntity = userRepository.save(user);
        return userEntity;
    }

    @Transactional
    public String signin(SignInRequest signInRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = signInRequest.toAuthentication();
        Authentication authenticate = authenticationManager.getObject().authenticate(authenticationToken);
        System.out.println("authenticate = " + authenticate);
        tokenHelper.generateTokenDto(authenticate);

        return "";
    }
}
