package com.cos.photogramstart.global.error.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class PasswordMisMatchException extends BadCredentialsException {
    public PasswordMisMatchException(String msg) {
        super(msg);
    }
}
