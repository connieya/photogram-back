package com.cos.photogramstart.domain.likes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LikesRepository extends JpaRepository<Likes, Integer> {

    @Modifying
    @Query(value = "insert into likes(imagdId , userId) values(:imageId , :principalId)" , nativeQuery = true)
    int mLike(int imageId , int principalId);

    @Modifying
    @Query(value = "delete from likes where imageId =:imageId and userId =:userId" , nativeQuery = true)
    int mUnLike(int imageId , int principalId);
}
