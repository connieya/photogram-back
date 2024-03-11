package com.cos.photogramstart.domain.user.infrastructure;

import com.cos.photogramstart.domain.user.application.result.UserProfileResult;

public interface UserRepositoryQuerydsl {

    UserProfileResult findUserProfile(Long loginUserId, String username);
}
