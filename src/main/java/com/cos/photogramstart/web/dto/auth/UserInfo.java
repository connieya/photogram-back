package com.cos.photogramstart.web.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfo {

    private int id;
    private String username;
    private String password;
    private String email;
    private String nickname;
    private String role;
    private String profileImageUrl;
}
