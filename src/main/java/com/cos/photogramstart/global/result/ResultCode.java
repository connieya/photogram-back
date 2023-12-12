package com.cos.photogramstart.global.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/*
 *  도메인 별로 관리
 *
 * */
@Getter
@AllArgsConstructor
public enum ResultCode {
    // Member
    REGISTER_SUCCESS(200, "M001", "회원가입에 성공하였습니다."),
    LOGIN_SUCCESS(200, "M002", "로그인에 성공하였습니다."),

    // Follow
    FOLLOW_SUCCESS(200, "F001", "회원 팔로우를 성공하였습니다."),
    UNFOLLOW_SUCCESS(200, "F002", "회원 언팔로우를 성공하였습니다."),


    // Feed
    CREATE_POST_SUCCESS(200, "F001", "게시물 업로드에 성공하였습니다."),
    LIKE_POST_SUCCESS(200, "F006", "게시물 좋아요에 성공하였습니다.");

    private final int status;
    private final String code;
    private final String message;

}
