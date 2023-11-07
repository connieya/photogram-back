package com.cos.photogramstart.service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.config.jwt.JWTTokenHelper;
import com.cos.photogramstart.domain.token.RefreshToken;
import com.cos.photogramstart.domain.token.RefreshTokenRepository;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.web.dto.RespDto;
import com.cos.photogramstart.web.dto.auth.SignInRequest;
import com.cos.photogramstart.web.dto.auth.SignInResponse;
import com.cos.photogramstart.web.dto.auth.UserInfo;
import com.cos.photogramstart.web.dto.jwt.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final RefreshTokenRepository refreshTokenRepository;


    @Transactional
    public User signup(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new CustomApiException("사용중인 아이디 입니다.");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new CustomApiException("사용중인 이메일 입니다.");
        }
        String rawPassword = user.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        User userEntity = userRepository.save(user);
        return userEntity;
    }


    @Transactional
    public ResponseEntity<?> signin(SignInRequest signInRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = signInRequest.toAuthentication();
        Authentication authenticate;
        try {
            authenticate = authenticationManager.getObject().authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.OK).body(new RespDto<>(-1,"비밀번호가 일치 하지 않습니다.",null));
        }
        TokenDto tokenDto = tokenHelper.generateTokenDto(authenticate);
        PrincipalDetails principal = (PrincipalDetails) authenticate.getPrincipal();
        User user = principal.getUser();
        UserInfo userInfo = UserInfo.builder()
                .username(user.getUsername())
                .id(user.getId())
                .profileImageUrl(user.getProfileImageUrl()).build();
        return ResponseEntity.status(HttpStatus.OK).body(new RespDto<>(1,"로그인 성공",new SignInResponse(tokenDto,userInfo)));

//        RefreshToken refreshToken = RefreshToken.builder()
//                .key(authenticate.getName())
//                .value(tokenDto.getRefreshToken())
//                .build();
//        refreshTokenRepository.save(refreshToken);

    }

    @Transactional
    public TokenDto reissue(TokenDto tokenDto) {
        if (!tokenHelper.validateToken(tokenDto.getRefreshToken())) {
            throw new RuntimeException("Refreh Token이 유효하지 않습니다.");
        }

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
}
