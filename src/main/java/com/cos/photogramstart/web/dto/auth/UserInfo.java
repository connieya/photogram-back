package com.cos.photogramstart.web.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserInfo {

    private int id;
    private String username;
    private String profileImageUrl;
}
