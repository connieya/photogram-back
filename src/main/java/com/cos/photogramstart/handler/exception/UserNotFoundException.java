package com.cos.photogramstart.handler.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class UserNotFoundException extends BadCredentialsException {
    public UserNotFoundException(String msg) {
        super(msg);
    }
}
