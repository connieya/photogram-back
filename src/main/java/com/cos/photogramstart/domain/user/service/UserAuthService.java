package com.cos.photogramstart.domain.user.service;

import com.cos.photogramstart.global.config.security.auth.PrincipalDetails;
import com.cos.photogramstart.global.config.security.TokenProvider;
import com.cos.photogramstart.domain.token.RefreshToken;
import com.cos.photogramstart.domain.token.RefreshTokenRepository;
import com.cos.photogramstart.domain.user.entity.User;
import com.cos.photogramstart.domain.user.repository.UserRepository;
import com.cos.photogramstart.handler.exception.*;
import com.cos.photogramstart.domain.user.dto.SignInRequest;
import com.cos.photogramstart.domain.user.dto.SignInResponse;
import com.cos.photogramstart.web.dto.auth.UserInfo;
import com.cos.photogramstart.web.dto.jwt.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service // Ioc , 트랜잭션관리
public class UserAuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManager;
    private final TokenProvider tokenHelper;
    private final RefreshTokenRepository refreshTokenRepository;


    @Transactional
    public User signup(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new DuplicateUserNameException("사용중인 아이디 입니다.");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateEmailException("사용중인 이메일 입니다.");
        }
        String rawPassword = user.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        return userRepository.save(user);
    }


    @Transactional
    public SignInResponse signin(SignInRequest signInRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = signInRequest.toAuthentication();
        Authentication authenticate;
        try {
            authenticate = authenticationManager.getObject().authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new PasswordMisMatchException("비밀번호가 일치하지 않습니다.");
        }
        TokenDto tokenDto = tokenHelper.generateTokenDto(authenticate);
        PrincipalDetails principal = (PrincipalDetails) authenticate.getPrincipal();
        User user = principal.getUser();
        UserInfo userInfo = UserInfo.builder()
                .username(user.getUsername())
                .id(user.getId())
                .build();
        return new SignInResponse(tokenDto.getAccessToken(),userInfo);
//        RefreshToken refreshToken = RefreshToken.builder()
//                .key(authenticate.getName())
//                .value(tokenDto.getRefreshToken())
//                .build();
//        refreshTokenRepository.save(refreshToken);

    }

    @Transactional
    public TokenDto reissue(TokenDto tokenDto) {
        tokenHelper.validateToken(tokenDto.getRefreshToken());

        Authentication authentication = tokenHelper.getAuthentication(tokenDto.getAccessToken());
        RefreshToken refreshToken = refreshTokenRepository.
                findByKey(authentication.getName()).orElseThrow(() -> new RuntimeException("로그아웃 된 사용자 입니다."));

        if (!refreshToken.getValue().equals(tokenDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다");
        }
        TokenDto newToken = tokenHelper.generateTokenDto(authentication);
        RefreshToken newRefreshToken = refreshToken.updateValue(newToken.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);
        return newToken;
    }

    @Transactional
    public void changePassword(SignInRequest signInRequest) {
        User user = userRepository.findByUsername(signInRequest.getUsername()).orElse(null);
        if (user != null) {
            String newPassword = passwordEncoder.encode(signInRequest.getPassword());
            user.setPassword(newPassword);
            userRepository.save(user);
        }

    }
}
