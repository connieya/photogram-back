package com.cos.photogramstart.global.config.security.auth;

import com.cos.photogramstart.domain.user.domain.User;
import com.cos.photogramstart.domain.user.infrastructure.UserRepository;
import com.cos.photogramstart.global.error.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class PrincipleDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("로그인 요청 ! 사용자 이름 = {}  ", username);
        User userEntity = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("존재하지 않는 아이디 입니다."));
        return new PrincipalDetails(userEntity);
    }
}
