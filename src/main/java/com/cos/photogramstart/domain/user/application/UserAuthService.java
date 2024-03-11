package com.cos.photogramstart.domain.user.application;

import com.cos.photogramstart.domain.user.application.command.SignUpCommand;
import com.cos.photogramstart.domain.user.application.result.SignInResult;
import com.cos.photogramstart.global.config.security.auth.PrincipalDetails;
import com.cos.photogramstart.global.config.security.TokenProvider;
import com.cos.photogramstart.domain.token.RefreshToken;
import com.cos.photogramstart.domain.token.RefreshTokenRepository;
import com.cos.photogramstart.domain.user.domain.User;
import com.cos.photogramstart.domain.user.infrastructure.UserRepository;
import com.cos.photogramstart.global.error.ErrorCode;
import com.cos.photogramstart.global.error.exception.EntityAlreadyExistException;
import com.cos.photogramstart.global.error.exception.PasswordMisMatchException;
import com.cos.photogramstart.domain.user.presentation.request.SignInRequest;
import com.cos.photogramstart.global.config.security.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.cos.photogramstart.global.error.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class UserAuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManager;
    private final TokenProvider tokenHelper;
    private final RefreshTokenRepository refreshTokenRepository;


    @Transactional
    public void signup(SignUpCommand signUpCommand) {
        if (userRepository.existsByUsername(signUpCommand.getUsername())) {
            throw new EntityAlreadyExistException(USERNAME_ALREADY_EXIST);
        }
        if (userRepository.existsByEmail(signUpCommand.getEmail())){
            throw new EntityAlreadyExistException(EMAIL_ALREADY_EXIST);
        }
        String rawPassword = signUpCommand.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);
        signUpCommand.encPassword(encPassword);
        User user = User.create(signUpCommand);
        userRepository.save(user);
    }


    @Transactional
    public SignInResult signin(SignInRequest signInRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = signInRequest.toAuthentication();
        Authentication authenticate;
        try {
            authenticate = authenticationManager.getObject().authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new PasswordMisMatchException(e.getMessage());
        }
        TokenDto tokenDto = tokenHelper.generateTokenDto(authenticate);
        PrincipalDetails principal = (PrincipalDetails) authenticate.getPrincipal();
        User user = principal.getUser();
        return SignInResult.create(user, tokenDto.getAccessToken());

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


}
