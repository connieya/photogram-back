package com.cos.photogramstart.domain.comment;

import com.cos.photogramstart.web.dto.comment.CommentResponseDto;

import java.util.List;

public interface CommentRepositoryCustom {
    List<CommentResponseDto> findByImageId(int imageId);
}
