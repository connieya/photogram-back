package com.cos.photogramstart.global.error.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class TokenMissingException extends BadCredentialsException {
    public TokenMissingException(String msg) {
        super(msg);
    }
}
