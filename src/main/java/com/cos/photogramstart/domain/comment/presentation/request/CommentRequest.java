package com.cos.photogramstart.domain.comment.presentation.request;

import com.cos.photogramstart.domain.comment.application.CommentCommand;
import lombok.Getter;

@Getter
public class CommentRequest {
    private Long imageId;
    private String content;

    public CommentCommand toCommand(){
        return CommentCommand
                .builder()
                .imageId(imageId)
                .content(content)
                .build();
    }
}
