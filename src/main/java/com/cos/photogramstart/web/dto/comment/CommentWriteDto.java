package com.cos.photogramstart.web.dto.comment;

import lombok.Getter;

@Getter
public class CommentWriteDto {
    private int commentId;
    private String content;
    private int imageId;
}
