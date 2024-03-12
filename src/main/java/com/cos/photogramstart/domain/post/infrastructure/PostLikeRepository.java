package com.cos.photogramstart.domain.post.infrastructure;

import com.cos.photogramstart.domain.post.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    boolean existsByPostIdAndUserId(Long postId , Long userId);

    PostLike findByPostIdAndUserId(Long postId, Long userId);
}
