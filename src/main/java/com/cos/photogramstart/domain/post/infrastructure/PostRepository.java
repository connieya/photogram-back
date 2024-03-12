package com.cos.photogramstart.domain.post.infrastructure;

import com.cos.photogramstart.domain.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface PostRepository extends JpaRepository<Post, Long> , PostCustomRepository {

    @Query(value = "SELECT * FROM image WHERE userId in \n" +
            "(SELECT toUserId FROM follow WHERE fromUserId = :principalId ) ORDER BY id DESC" , nativeQuery = true)
    Page<Post> mStory(int principalId, Pageable pageable);
}
