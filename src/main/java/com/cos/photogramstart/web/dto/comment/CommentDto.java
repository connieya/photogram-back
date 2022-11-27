package com.cos.photogramstart.web.dto.comment;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CommentDto {
    @NotBlank // 빈 값이거나 null 체크 , 빈 공백
    private String content;
    @NotNull // 빈 값 체크
    private Integer imageId;

}
