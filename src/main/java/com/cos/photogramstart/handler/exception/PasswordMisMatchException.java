package com.cos.photogramstart.handler.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class PasswordMisMatchException extends BadCredentialsException {
    public PasswordMisMatchException(String msg) {
        super(msg);
    }
}
