package com.cos.photogramstart.domain.user.application;

import com.cos.photogramstart.domain.user.application.command.UserUpdateCommand;
import com.cos.photogramstart.domain.user.application.result.UserProfileResult;
import com.cos.photogramstart.domain.user.domain.Image;
import com.cos.photogramstart.global.error.exception.EntityAlreadyExistException;
import com.cos.photogramstart.global.error.exception.EntityNotFoundException;
import com.cos.photogramstart.global.util.AuthUtil;
import com.cos.photogramstart.domain.folllow.infrastructure.FollowRepository;
import com.cos.photogramstart.domain.user.domain.User;
import com.cos.photogramstart.domain.user.infrastructure.UserRepository;
import com.cos.photogramstart.global.aws.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public void update(UserUpdateCommand userUpdateCommand) {
        if (userRepository.existsByUsername(userUpdateCommand.getUsername())){
            throw new EntityAlreadyExistException(USERNAME_ALREADY_EXIST);
        }
        User user = authUtil.getLoginUser();
        user = user.update(userUpdateCommand);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public UserProfileResult getUserProfile(String username) {
        User user = authUtil.getLoginUser();
        User pageUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        UserProfileResult userProfile = userRepository.findUserProfile(user, username);
        userProfile.setFollowerCount(followRepository.followerCount(pageUser.getId()));
        userProfile.setFollowingCount(followRepository.followingCount(pageUser.getId()));
        return userProfile;
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
        s3Uploader.deleteImage(loginUser.getImage(), USER_S3_DIRNAME);
        loginUser.deleteImage();
        userRepository.save(loginUser);
    }
}
