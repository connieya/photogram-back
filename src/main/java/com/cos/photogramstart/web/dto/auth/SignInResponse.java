package com.cos.photogramstart.web.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignInResponse {
//    private TokenDto tokenDto;
    private String accessToken;
    private UserInfo userInfo;
}

