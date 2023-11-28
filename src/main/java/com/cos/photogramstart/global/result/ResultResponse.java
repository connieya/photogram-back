package com.cos.photogramstart.global.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel(description = "결과 응답 데이터 모델")
@Getter
public class ResultResponse {

    @ApiModelProperty(value = "Http 상태 코드")
    private int status;
    @ApiModelProperty(value = "Business 상태 코드")
    private String code;
    @ApiModelProperty(value = "응답 메시지")
    private String message;
    @ApiModelProperty(value = "응답 데이터")
    private Object data;
}
