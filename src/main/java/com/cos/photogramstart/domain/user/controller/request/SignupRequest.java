package com.cos.photogramstart.domain.user.controller.request;

import com.cos.photogramstart.domain.user.repository.User;
import com.cos.photogramstart.domain.user.service.command.SignUpCommand;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignupRequest {
    @Size(min = 2 , max = 20)
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String email;
    @NotBlank
    private String name;

    public SignUpCommand toCommand() {
        return SignUpCommand
                .builder()
                .username(username)
                .password(password)
                .email(email)
                .name(name)
                .build();
    }


}