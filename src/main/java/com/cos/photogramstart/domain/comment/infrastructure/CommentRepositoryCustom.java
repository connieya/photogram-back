package com.cos.photogramstart.domain.comment.infrastructure;

import com.cos.photogramstart.domain.comment.application.CommentResult;

import java.util.List;

public interface CommentRepositoryCustom {
    List<CommentResult> findByPostId(Long postId);
}
