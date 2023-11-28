package com.cos.photogramstart.domain.post.repository;

import com.cos.photogramstart.domain.post.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImage,Long> {
}
