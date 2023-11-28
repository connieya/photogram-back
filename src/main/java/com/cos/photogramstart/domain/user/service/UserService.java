package com.cos.photogramstart.domain.user.service;

import com.cos.photogramstart.global.common.Image;
import com.cos.photogramstart.global.util.AuthUtil;
import com.cos.photogramstart.domain.folllow.repository.FollowRepository;
import com.cos.photogramstart.domain.user.entity.User;
import com.cos.photogramstart.domain.user.repository.UserRepository;
import com.cos.photogramstart.handler.exception.CustomApiException;
import com.cos.photogramstart.handler.exception.CustomValidationApiException;
import com.cos.photogramstart.handler.exception.UserNotFoundException;
import com.cos.photogramstart.global.aws.S3Uploader;
import com.cos.photogramstart.web.dto.auth.UserInfo;
import com.cos.photogramstart.web.dto.user.UserProfileDto;
import com.cos.photogramstart.web.dto.user.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final String USER_S3_DIRNAME = "user";
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final S3Uploader s3Uploader;
    private final AuthUtil authUtil;

    @Transactional(readOnly = true)
    public List<UserInfo> selectUsers() {
        return null;

    }

    @Transactional(readOnly = true)
    public UserProfileDto selectUserProfile(int pageUserId, int principalId) {
        User userEntity = userRepository.findById(pageUserId).orElseThrow(() -> {
            throw new CustomApiException("해당 프로필 페이지는 없는 페이지입니다.");
        });
        int followState = followRepository.followState(principalId, pageUserId);
        UserProfileDto dto = UserProfileDto
                .builder().bio(userEntity.getBio())
                .webSite(userEntity.getWebsite())
                .imageCount(userEntity.getImages().size())
                .username(userEntity.getUsername())
                .followState(followState == 1).build();
        return dto;
    }

    @Transactional
    public User update(int id, User user) {
        // 영속화
        User userEntity = userRepository.findById(id).orElseThrow(() ->
                new CustomValidationApiException("찾을 수 없는 id 입니다.")
        );
        userEntity.setUsername(user.getUsername());
        userEntity.setBio(user.getBio());
        userEntity.setWebsite(user.getWebsite());
        return userEntity;
    }

    @Transactional
    public User updateImage(int principalId, MultipartFile profileImageFile) {
        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "_" + profileImageFile.getOriginalFilename();
        User userEntity = userRepository.findById(principalId).orElseThrow(() -> {
            throw new CustomApiException("유저를 찾을 수 없습니다.");
        });
        return userEntity;
    }

    public UserProfileResponse getUserProfile(String username) {
        final User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("존재 하지 않는 유저입니다."));
        return UserProfileResponse.builder()
                .name(user.getName())
                .username(user.getUsername())
                .website(user.getWebsite()).build();
    }

    @Transactional
    public void uploadProfileImage(MultipartFile uploadedImage) {
        User loginUser = authUtil.getLoginUser();
        Image image = s3Uploader.uploadImage(uploadedImage, USER_S3_DIRNAME);
        loginUser.setImage(image);
        userRepository.save(loginUser);
    }

    @Transactional
    public void deleteProfileImage() {
        User loginUser = authUtil.getLoginUser();
        s3Uploader.deleteImage(loginUser.getImage(),USER_S3_DIRNAME);
        loginUser.deleteImage();
        userRepository.save(loginUser);
    }
}
