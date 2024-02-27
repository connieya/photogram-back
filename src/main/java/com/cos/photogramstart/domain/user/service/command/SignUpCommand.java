package com.cos.photogramstart.domain.user.service.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignUpCommand {

    private String username;
    private String password;
    private String email;
    private String name;

    public void encPassword(String password){
        this.password = password;
    }

}
