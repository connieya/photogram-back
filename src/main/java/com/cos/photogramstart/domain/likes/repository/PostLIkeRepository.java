package com.cos.photogramstart.domain.likes.repository;

import com.cos.photogramstart.domain.likes.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PostLIkeRepository extends JpaRepository<PostLike, Integer> {

    @Modifying
    @Query(value = "insert into PostLikes(imageId , userId ,createDate) values(:imageId , :principalId , now())" , nativeQuery = true)
    int mLike(int imageId , int principalId);

    @Modifying
    @Query(value = "delete from PostLikes where imageId =:imageId and userId =:principalId" , nativeQuery = true)
    int mUnLike(int imageId , int principalId);

//    PostLike findByImageIdAndUserId(int imageId , int userId);
}
