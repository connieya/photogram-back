package com.cos.photogramstart.domain.post.infrastructure;

import com.cos.photogramstart.domain.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface PostRepository extends JpaRepository<Post, Long> , PostCustomRepository {
    Optional<Post> findByUserId(Long id);
}
