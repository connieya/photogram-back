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
    USER_PROFILE_PUT_SUCCESS(200, "U006", "유저 프로필 수정에 성공하였습니다."),
    USER_LIST_GET_SUCCESS(200, "U007", "유저 목록 조회에 성공하였습니다."),

    // JWT
    REISSUE_SUCCESS(200, "J001", "재발급에 성공하였습니다."),


    // Follow
    FOLLOW_SUCCESS(200, "F001", "회원 팔로우를 성공하였습니다."),
    UNFOLLOW_SUCCESS(200, "F002", "회원 언팔로우를 성공하였습니다."),
    GET_FOLLOWINGS_SUCCESS(200, "F003", "회원 팔로잉 목록을 조회하였습니다."),
    GET_FOLLOWERS_SUCCESS(200, "F004", "회원 팔로워 목록을 조회하였습니다."),

    // Comment
    COMMENT_POST_SUCCESS(200,"C001","댓글 쓰기에 성공 하였습니다."),
    COMMENT_DELETE_SUCCESS(200,"C002","댓글 삭제에 성공 하였습니다."),
    COMMENT_GET_SUCCESS(200,"C003","댓글 조회에 성공 하였습니다."),

    // Feed
    CREATE_POST_SUCCESS(200, "F001", "게시물 업로드에 성공 하였습니다."),
    LIKE_POST_SUCCESS(200, "F006", "게시물 좋아요에 성공 하였습니다."),
    UN_LIKE_POST_SUCCESS(200, "F007", "게시물 좋아요 취소에 성공 하였습니다."),
    POPULAR_FEED_GET_SUCCESS(200, "F008", "인기 게시물 조회에 성공 하였습니다.");

    private final int status;
    private final String code;
    private final String message;

}
