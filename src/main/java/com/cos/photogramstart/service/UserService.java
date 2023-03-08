package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.folllow.FollowRepository;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FollowRepository subscribeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${file.path}")
    private String uploadFolder;

    @Transactional(readOnly = true)
    public UserProfileDto select(int pageUserId, int principalId){
        UserProfileDto dto = new UserProfileDto();
        User userEntity = userRepository.findById(pageUserId).orElseThrow(() -> {
            throw new CustomException("해당 프로필 페이지는 없는 페이지입니다.");
        });
        dto.setUser(userEntity);
        dto.setPageOwner(pageUserId == principalId);
        dto.setImageCount(userEntity.getImages().size());

        int subscribeState = subscribeRepository.mSubscribeState(principalId, pageUserId);
        int subscribeCount = subscribeRepository.mSubscribeCount(pageUserId);
        int subscribedCount = subscribeRepository.mSubscribedCount(pageUserId);

        dto.setSubscribeState(subscribeState == 1);
        dto.setSubscribeCount(subscribeCount);
        dto.setSubscribedCount(subscribedCount);

        // 좋아요 개수
        userEntity.getImages().forEach(image -> {
            image.setLikeCount(image.getLikes().size());
        });
        return dto;
    }

    @Transactional
    public User update(int id, User user) {
        // 영속화
        User userEntity = userRepository.findById(id).orElseThrow(()->
          new CustomValidationApiException("찾을 수 없는 id 입니다.")
        );
        userEntity.setNickname(user.getNickname());
//        String rawPassword = user.getPassword();
//        String encode = bCryptPasswordEncoder.encode(rawPassword);
//        userEntity.setPassword(encode);
        userEntity.setBio(user.getBio());
        userEntity.setWebsite(user.getWebsite());
        return userEntity;
    }

    @Transactional
    public User updateImage(int principalId, MultipartFile profileImageFile) {
        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "_" + profileImageFile.getOriginalFilename();
        Path imageFilePath = Paths.get(uploadFolder + imageFileName);
        try{
            Files.write(imageFilePath , profileImageFile.getBytes());
        }catch(Exception e){
            e.printStackTrace();
        }
        User userEntity = userRepository.findById(principalId).orElseThrow(() -> {
            throw new CustomApiException("유저를 찾을 수 없습니다.");
        });
        userEntity.setProfileImageUrl(imageFileName);
        return userEntity;
    }
}
