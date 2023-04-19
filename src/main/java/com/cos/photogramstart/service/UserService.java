package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.folllow.FollowRepository;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.web.dto.auth.UserInfo;
import com.cos.photogramstart.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    @Value("${file.path}")
    private String uploadFolder;

    @Transactional(readOnly = true)
    public List<UserInfo> selectUsers(){
        return userRepository.findAll()
                .stream()
                .map(u -> new UserInfo(u.getId(),u.getUsername(),u.getProfileImageUrl()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserProfileDto selectUserProfile(int pageUserId, int principalId){
        UserProfileDto dto = new UserProfileDto();
        User userEntity = userRepository.findById(pageUserId).orElseThrow(() -> {
            throw new CustomApiException("해당 프로필 페이지는 없는 페이지입니다.");
        });
        dto.setUser(userEntity);
        dto.setPageOwner(pageUserId == principalId);
        dto.setImageCount(userEntity.getImages().size());

        int followState = followRepository.followState(principalId, pageUserId);
        int followingCount = followRepository.followingCount(pageUserId);
        int followerCount = followRepository.followerCount(pageUserId);

        dto.setFollowState(followState == 1);
        dto.setFollowingCount(followingCount);
        dto.setFollowerCount(followerCount);
        return dto;
    }

    @Transactional
    public User update(int id, User user) {
        // 영속화
        User userEntity = userRepository.findById(id).orElseThrow(()->
          new CustomValidationApiException("찾을 수 없는 id 입니다.")
        );
        userEntity.setNickname(user.getNickname());
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
