package com.cos.photogramstart.web.dto.auth;

import lombok.Data;

@Data
public class SignupDto {
    private String usename;
    private String password;
    private String email;
    private String name;
}
