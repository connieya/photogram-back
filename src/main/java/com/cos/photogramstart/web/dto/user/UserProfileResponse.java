package com.cos.photogramstart.web.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileResponse {

    private String username;
    private String name;
    private String website;


}
