package com.cos.photogramstart.global.response;

import lombok.Getter;

@Getter
public abstract class BaseResponse<T> {

    private final boolean isSuccess;

    private final String message;

    private final int code;

    public BaseResponse(ResponseEnum status) {
        this.isSuccess = status.isSuccess();
        this.message = status.getMessage();
        this.code = status.getCode();
    }
}
