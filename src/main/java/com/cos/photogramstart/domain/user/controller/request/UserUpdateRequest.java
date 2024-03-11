package com.cos.photogramstart.domain.user.controller.request;

import com.cos.photogramstart.domain.user.repository.User;
import com.cos.photogramstart.domain.user.service.command.UserUpdateCommand;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserUpdateRequest {
    @NotBlank
    private String username;
    private String website;
    private String bio;


    public UserUpdateCommand toCommand() {
        return UserUpdateCommand
                .builder()
                .username(username)
                .website(website)
                .bio(bio)
                .build();
    }
}
