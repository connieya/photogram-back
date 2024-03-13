package com.cos.photogramstart.domain.post.exception;

import com.cos.photogramstart.global.error.ErrorCode;
import com.cos.photogramstart.global.error.exception.BusinessException;

public class PostDeleteException extends BusinessException {
    public PostDeleteException(ErrorCode errorCode) {
        super(errorCode);
    }
}
