package com.cos.photogramstart.domain.user.repository.querydsl;

import com.cos.photogramstart.domain.user.repository.result.UserProfileResponse;

public interface UserRepositoryQuerydsl {

    UserProfileResponse findUserProfile(Long loginUserId, String username);
}
