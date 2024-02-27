package com.cos.photogramstart.domain.user.service.result;

import com.cos.photogramstart.domain.user.repository.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SignInResult {

    private User user;
    private String accessToken;

    @Builder
    private SignInResult(User user, String accessToken) {
        this.user = user;
        this.accessToken = accessToken;
    }

    public static SignInResult create(User user , String accessToken){
        return  SignInResult
                .builder()
                .user(user)
                .accessToken(accessToken)
                .build();
    }
}
