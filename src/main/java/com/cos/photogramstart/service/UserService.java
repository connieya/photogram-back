package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User select(int userId){
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new CustomException("해당 프로필 페이지는 없는 페이지입니다.");
        });
        return user;
    }

    @Transactional
    public User update(int id, User user) {
        // 영속화
        User userEntity = userRepository.findById(id).orElseThrow(()->{
            return new CustomValidationApiException("찾을 수 없는 id 입니다.");
        });
        userEntity.setNickname(user.getNickname());
        String rawPassword = user.getPassword();
        String encode = bCryptPasswordEncoder.encode(rawPassword);
        userEntity.setPassword(encode);
        userEntity.setBio(user.getBio());
        userEntity.setWebsite(user.getWebsite());
        userEntity.setPhone(user.getPhone());
        userEntity.setGender(user.getGender());
        return userEntity;
    }
}
