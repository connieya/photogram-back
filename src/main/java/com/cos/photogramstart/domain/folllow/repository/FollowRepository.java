package com.cos.photogramstart.domain.folllow.repository;

import com.cos.photogramstart.domain.folllow.entity.Follow;
import com.cos.photogramstart.domain.folllow.repository.querydsl.FollowRepositoryQuerydsl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FollowRepository extends JpaRepository<Follow,Integer> , FollowRepositoryQuerydsl {


    boolean existsByFromUserIdAndToUserId(Long fromUserId , Long toUserId);

    @Modifying
    @Query(value = "delete from  follow where fromUserId =:fromUserId and toUserId =:toUserId", nativeQuery = true)
    void mUnfollow(int fromUserId , int toUserId);
}
