package com.cos.photogramstart.global.response;

import lombok.Getter;

@Getter
public enum ResponseEnum {
    SUCCESS(true, 1000, "요청에 성공하였습니다."),
    SIGNUP_SUCCESS(true, 1001, "회원 가입 성공"),
    SIGNIN_SUCCESS(true, 1002, "로그인 성공"),
    UPLOAD_SUCCESS(true, 1003, "게시물 등록 성공"),
    UPLOAD_PROFILE_IMAGE_SUCCESS(true, 1004, "프로필 이미지 등록 성공"),
    NOT_FOUND_USER(false, 4001, "존재하지 않는 아이디 입니다."),
    PASSWORD_MISMATCH(false, 4002, "비밀번호가 일치하지 않습니다."),
    TOKEN_MISSING(false, 4003, "토큰이 없습니다."),
    DUPLICATE_USER_NAME(false, 4005, "이미 존재하는 사용자 이름입니다."),
    D1UPLICATE_EMAIL(false, 4006, "이미 존재하는 이메일입니다."),
    INVALID_JWT_SIGNATURE(false, 4007, "잘못된 JWT 서명입니다."),
    EXPIRED_JWT(false, 4008, "만료된 JWT 토큰입니다."),
    UNSUPPORTED_JWT(false, 4009, "지원되지 않는 JWT 토큰입니다."),
    FILE_CONVERT_FAIL(false, 50001, "변환할 수 없는 파일입니다.");


    private final boolean isSuccess;
    private final int code;
    private final String message;

    ResponseEnum(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }

}