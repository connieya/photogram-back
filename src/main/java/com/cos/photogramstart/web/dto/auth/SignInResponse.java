package com.cos.photogramstart.web.dto.auth;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.web.dto.jwt.TokenDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignInResponse {
    private TokenDto tokenDto;
    private UserInfo userInfo;
}

