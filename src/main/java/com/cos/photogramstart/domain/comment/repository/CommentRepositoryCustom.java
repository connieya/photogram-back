package com.cos.photogramstart.domain.comment.repository;

import com.cos.photogramstart.domain.comment.service.CommentResponseDto;

import java.util.List;

public interface CommentRepositoryCustom {
    List<CommentResponseDto> findByImageId(int imageId);
}
