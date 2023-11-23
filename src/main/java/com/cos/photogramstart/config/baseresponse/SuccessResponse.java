package com.cos.photogramstart.config.baseresponse;

import lombok.Getter;

@Getter
public class SuccessResponse<T> extends BaseResponse {

    private T result;

    public SuccessResponse(T result) {
        super(ResponseEnum.SUCCESS);
        this.result = result;
    }



    public SuccessResponse(ResponseEnum status, T result) {
        super(status);
        this.result = result;
    }

    public SuccessResponse(ResponseEnum status) {
        super(status);
    }

    public SuccessResponse() {
        super(ResponseEnum.SUCCESS);
    }
}
