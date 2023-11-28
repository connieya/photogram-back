package com.cos.photogramstart.global.config.security.auth;

import com.cos.photogramstart.domain.user.entity.User;
import com.cos.photogramstart.domain.user.repository.UserRepository;
import com.cos.photogramstart.handler.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthUtil {

    private final UserRepository userRepository;

    public User getLoginUser() {
        System.out.println("@@@@" + SecurityContextHolder.getContext().getAuthentication().getName());
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new UserNotFoundException("존재하지 않는 유저입니다."));
        } catch (Exception e) {
            throw new RuntimeException();
        }

    }
}
