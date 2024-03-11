package com.cos.photogramstart.domain.folllow.exception;

import com.cos.photogramstart.global.error.ErrorCode;
import com.cos.photogramstart.global.error.exception.BusinessException;

public class UnfollowFailException extends BusinessException {
    public UnfollowFailException(ErrorCode errorCode) {
        super(errorCode);
    }
}
