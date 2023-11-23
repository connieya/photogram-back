package com.cos.photogramstart.web.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserProfileUpdateResponse {
    private int id;
    private String username;
    private String name;
    private String bio;
    private String email;
    private String website;
    private String profileImageUrl;
}
