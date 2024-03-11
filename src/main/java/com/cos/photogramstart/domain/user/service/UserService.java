package com.cos.photogramstart.domain.user.service;

import com.cos.photogramstart.domain.user.controller.request.UserUpdateRequest;
import com.cos.photogramstart.domain.user.service.command.UserUpdateCommand;
import com.cos.photogramstart.global.common.Image;
import com.cos.photogramstart.global.error.exception.EntityNotFoundException;
import com.cos.photogramstart.global.util.AuthUtil;
import com.cos.photogramstart.domain.folllow.repository.FollowRepository;
import com.cos.photogramstart.domain.user.repository.User;
import com.cos.photogramstart.domain.user.repository.UserRepository;
import com.cos.photogramstart.global.error.exception.CustomValidationApiException;
import com.cos.photogramstart.global.aws.S3Uploader;
import com.cos.photogramstart.domain.user.controller.response.UserInfo;
import com.cos.photogramstart.domain.user.repository.result.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

import static com.cos.photogramstart.global.error.ErrorCode.*;

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
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user-> new UserInfo(user.getId(),user.getUsername()))
                .collect(Collectors.toList());

    }

    @Transactional
    public void update(UserUpdateCommand userUpdateCommand) {
        User loginUser = authUtil.getLoginUser();
        User user = userRepository.findById(loginUser.getId()).orElseThrow(() ->
                new EntityNotFoundException(USER_NOT_FOUND)
        );
        user = user.update(userUpdateCommand);
        userRepository.save(user);

    }

    @Transactional(readOnly = true)
    public UserProfileResponse getUserProfile(String username) {
        final User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        return userRepository.findUserProfile(user.getId(),username);
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
