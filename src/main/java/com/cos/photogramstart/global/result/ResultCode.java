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
    // USER
    REGISTER_SUCCESS(200, "U001", "회원가입에 성공하였습니다."),
    LOGIN_SUCCESS(200, "U002", "로그인에 성공하였습니다."),
    USER_GET_SUCCESS(200, "U003", "유저 프로필 조회에 성공하였습니다."),
    USER_PROFILE_POST_SUCCESS(200, "U004", "유저 프로필 업로드에 성공하였습니다."),
    USER_PROFILE_DELETE_SUCCESS(200, "U005", "유저 프로필 삭제에 성공하였습니다."),

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
