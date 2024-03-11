package com.cos.photogramstart.global.error.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class UserNotFoundException extends BadCredentialsException {
    public UserNotFoundException(String msg) {
        super(msg);
    }
}
