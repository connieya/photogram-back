package com.cos.photogramstart.web.dto.user;

import com.cos.photogramstart.domain.user.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserUpdateDto {
    @NotBlank
    private String nickname;
    private String website;
    private String bio;

    public User toEntity(){
        return User.builder()
                .nickname(nickname)
                .website(website)
                .bio(bio)
                .build();
    }
}
