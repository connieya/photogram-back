package com.cos.photogramstart.domain.comment.controller;

import lombok.Getter;

@Getter
public class CommentWriteDto {
    private int commentId;
    private String content;
    private int imageId;
}
