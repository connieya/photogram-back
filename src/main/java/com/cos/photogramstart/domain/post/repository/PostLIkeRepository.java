package com.cos.photogramstart.domain.post.repository;

import com.cos.photogramstart.domain.post.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PostLIkeRepository extends JpaRepository<PostLike, Long> {

    @Modifying
    @Query(value = "insert into PostLikes(imageId , userId ,createDate) values(:imageId , :principalId , now())" , nativeQuery = true)
    int mLike(int imageId , Long principalId);

    @Modifying
    @Query(value = "delete from PostLikes where imageId =:imageId and userId =:principalId" , nativeQuery = true)
    int mUnLike(int imageId , long principalId);

    boolean existsByPostIdAndUserId(Long postId , Long userId);

//    PostLike findByPostIdAndUserId(int imageId , int userId);
}
