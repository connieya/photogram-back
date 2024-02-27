package com.cos.photogramstart.domain.user.controller.response;

import com.cos.photogramstart.domain.user.repository.User;
import com.cos.photogramstart.domain.user.service.result.SignInResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignInResponse {
    private String accessToken;
    private UserInfo userInfo;

    public static SignInResponse from(SignInResult signInResult) {
        User user = signInResult.getUser();
        UserInfo userInfo = UserInfo.builder()
                .username(user.getUsername())
                .id(user.getId())
                .build();
        return  new SignInResponse(signInResult.getAccessToken(),userInfo);
    }
}

