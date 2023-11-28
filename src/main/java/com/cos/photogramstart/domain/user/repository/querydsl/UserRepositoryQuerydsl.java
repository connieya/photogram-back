package com.cos.photogramstart.domain.user.repository.querydsl;

import com.cos.photogramstart.domain.user.dto.UserProfileResponse;

public interface UserRepositoryQuerydsl {

    UserProfileResponse findUserProfile(Long loginUserId, String username);
}
