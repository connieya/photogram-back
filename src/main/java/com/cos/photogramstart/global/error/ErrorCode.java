package com.cos.photogramstart.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //Global
    INPUT_VALUE_INVALID(400, "G003", "유효하지 않은 입력입니다."),
    INPUT_TYPE_INVALID(400, "G004", "입력 타입이 유효하지 않습니다."),
    HTTP_MESSAGE_NOT_READABLE(400, "G005", "request message body가 없거나, 값 타입이 올바르지 않습니다."),

    JWT_INVALID(401, "J001", "유효하지 않은 토큰입니다."),
    JWT_EXPIRED(401, "J002", "만료된 토큰입니다."),

    // user
    USER_NOT_FOUND(400, "U001", "존재 하지 않는 유저입 니다."),
    USERNAME_ALREADY_EXIST(400, "U002", "이미 존재 하는 사용자 이름입니다."),
    PASSWORD_MISMATCH(400, "U003", "비밀번호가 일치 하지 않습니다."),
    EMAIL_ALREADY_EXIST(400, "U004", "이미 사용 중인 이메일 입니다."),

    // follow
    FOLLOW_ALREADY_EXIST(400, "F001", "이미 팔로우한 유저입니다."),
    UNFOLLOW_FAIL(400, "F002", "팔로우 하지 않은 유저를 팔로우 취소 할 수 없습니다."),
    FOLLOW_MYSELF_FAIL(400, "F003", "자기 자신을 팔로우 할 수 없습니다."),
    UNFOLLOW_MYSELF_FAIL(400, "F004", "자기 자신을 언팔로우 할 수 없습니다."),

    // Feed
    POST_NOT_FOUND(400, "F001", "존재하지 않는 게시물입니다."),
    POST_CANT_DELETE(400, "F002", "게시물 게시자만 삭제할 수 있습니다."),
    POST_LIKE_NOT_FOUND(400, "F003", "해당 게시물에 좋아요를 누르지 않은 회원입니다."),
    POST_LIKE_ALREADY_EXIST(400, "F004", "해당 게시물에 이미 좋아요를 누른 회원입니다.");

    private final int status;
    private final String code;
    private final String message;
}
