package com.cos.photogramstart.domain.likes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LikesRepository extends JpaRepository<Likes, Integer> {

    @Modifying
    @Query(value = "insert into likes(imageId , userId ,createDate) values(:imageId , :principalId , now())" , nativeQuery = true)
    int mLike(int imageId , int principalId);

    @Modifying
    @Query(value = "delete from likes where imageId =:imageId and userId =:principalId" , nativeQuery = true)
    int mUnLike(int imageId , int principalId);

    Likes findByImageIdAndUserId(int imageId , int userId);
}
