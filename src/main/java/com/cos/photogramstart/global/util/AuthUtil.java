package com.cos.photogramstart.global.util;

import com.cos.photogramstart.domain.user.repository.User;
import com.cos.photogramstart.domain.user.repository.UserRepository;
import com.cos.photogramstart.global.error.ErrorCode;
import com.cos.photogramstart.global.error.exception.EntityNotFoundException;
import com.cos.photogramstart.global.error.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static com.cos.photogramstart.global.error.ErrorCode.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthUtil {

    private final UserRepository userRepository;

    public User getLoginUser() {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        } catch (Exception e) {
            throw new RuntimeException();
        }

    }
}
