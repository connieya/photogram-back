package com.cos.photogramstart.domain.comment.application;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class CommentResult {
    private int commentId;
    private String content;
    private Long userId;
    private String username;

    @QueryProjection
    public CommentResult(int commentId, String content, Long userId, String username) {
        this.commentId = commentId;
        this.content = content;
        this.userId = userId;
        this.username = username;
    }
}
