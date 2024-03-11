package com.cos.photogramstart.domain.comment.application;

import lombok.Builder;
import lombok.Getter;

@Builder @Getter
public class CommentCommand {

    private Long imageId;
    private String content;
}
