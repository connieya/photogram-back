package com.cos.photogramstart.domain.post.infrastructure;

import com.cos.photogramstart.domain.post.application.PostResult;

import java.util.List;

public interface PostCustomRepository {
    List<PostResult> findAll(Long loginUserId);

}
