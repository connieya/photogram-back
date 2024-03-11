package com.cos.photogramstart.domain.user.application.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserUpdateCommand {

    private String name;
    private String username;
    private String website;
    private String bio;
}
