package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service // Ioc , 트랜잭션관리
public class AuthService {

    private final UserRepository userRepository;

    // 회원가입
    public User signup(User user){
        User userEntity = userRepository.save(user);
        return userEntity;
    }
}
