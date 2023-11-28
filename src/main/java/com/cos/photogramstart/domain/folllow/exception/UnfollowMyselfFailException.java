package com.cos.photogramstart.domain.folllow.exception;

import com.cos.photogramstart.global.error.ErrorCode;
import com.cos.photogramstart.global.error.exception.BusinessException;

public class UnfollowMyselfFailException extends BusinessException {
    public UnfollowMyselfFailException() {
        super(ErrorCode.UNFOLLOW_MYSELF_FAIL);
    }
}
