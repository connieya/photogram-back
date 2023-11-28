package com.cos.photogramstart.global.error.exception;

import com.cos.photogramstart.global.error.ErrorCode;

public class EntityAlreadyExistException extends BusinessException {
    public EntityAlreadyExistException(ErrorCode errorCode) {
        super(errorCode);
    }
}
