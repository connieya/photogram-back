package com.cos.photogramstart.domain.user.infrastructure;

import com.cos.photogramstart.domain.user.application.result.UserProfileResult;
import com.cos.photogramstart.domain.user.domain.User;

public interface UseCustomRepository {

    UserProfileResult findUserProfile(User loginUser, String username);
}
