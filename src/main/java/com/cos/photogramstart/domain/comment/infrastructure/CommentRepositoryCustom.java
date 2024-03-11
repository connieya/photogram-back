package com.cos.photogramstart.domain.comment.infrastructure;

import com.cos.photogramstart.domain.comment.application.CommentResponseDto;

import java.util.List;

public interface CommentRepositoryCustom {
    List<CommentResponseDto> findByImageId(int imageId);
}
