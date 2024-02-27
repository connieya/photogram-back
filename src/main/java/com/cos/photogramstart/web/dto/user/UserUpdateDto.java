package com.cos.photogramstart.web.dto.user;

import com.cos.photogramstart.domain.user.repository.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserUpdateDto {
    @NotBlank
    private String username;
    private String website;
    private String bio;

    public User toEntity(){
        return User.builder()
                .username(username)
                .website(website)
                .bio(bio)
                .build();
    }
}
