package com.cos.photogramstart.config.baseresponse;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
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
