package com.cos.photogramstart.domain.folllow.exception;

import com.cos.photogramstart.global.error.ErrorCode;
import com.cos.photogramstart.global.error.exception.BusinessException;

public class FollowMyselfFailException extends BusinessException {
    public FollowMyselfFailException() {
        super(ErrorCode.FOLLOW_MYSELF_FAIL);
    }
}
