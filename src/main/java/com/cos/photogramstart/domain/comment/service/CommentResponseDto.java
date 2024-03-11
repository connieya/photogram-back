package com.cos.photogramstart.domain.comment.service;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CommentResponseDto {
    private int contentId;
    @NotBlank // 빈 값이거나 null 체크 , 빈 공백
    private String content;
    private int userId;
    private String username;
}
