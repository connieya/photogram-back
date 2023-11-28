package com.cos.photogramstart.global.error.exception;

import com.cos.photogramstart.global.error.ErrorCode;

public class EntityNotFoundException extends BusinessException {
    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
