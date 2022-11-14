package com.cos.photogramstart.web.dto.auth;

import com.cos.photogramstart.domain.user.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignupDto {
    @Size(min = 2 , max = 20)
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String email;
    @NotBlank
    private String nickname;

    public User toEntity(){
        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .nickname(nickname)
                .build();
    }
}
