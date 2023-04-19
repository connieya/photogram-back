package com.cos.photogramstart.web.dto.comment;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CommentResponseDto {
    private int contentId;
    @NotBlank // 빈 값이거나 null 체크 , 빈 공백
    private String content;
    private int userId;
    private String username;
}
