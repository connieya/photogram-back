package com.cos.photogramstart.domain.post.infrastructure;

import com.cos.photogramstart.domain.post.domain.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImage,Long> {
}
