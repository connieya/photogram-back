package com.cos.photogramstart.domain.user.dto;

import com.cos.photogramstart.web.dto.auth.UserInfo;
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

