package com.cos.photogramstart.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Global
    INPUT_TYPE_INVALID(400, "G004", "입력 타입이 유효하지 않습니다."),

    // user
    USER_NOT_FOUND(400, "M001", "존재 하지 않는 유저입니다."),

    // follow
    FOLLOW_ALREADY_EXIST(400, "F001", "이미 팔로우한 유저입니다."),
    FOLLOW_MYSELF_FAIL(400, "F003", "자기 자신을 팔로우 할 수 없습니다.");

    private final int status;
    private final String code;
    private final String message;
}
