package com.cos.photogramstart.domain.folllow.repository;

import com.cos.photogramstart.domain.folllow.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FollowRepository extends JpaRepository<Follow,Integer> ,FollowRepositoryCustom{

    @Modifying
    @Query(value = "insert into follow(fromUserid ,toUserId , createDate) values(:fromUserId, :toUserId , now())", nativeQuery = true)
    void mFollow(int fromUserId, int toUserId);

    @Modifying
    @Query(value = "delete from  follow where fromUserId =:fromUserId and toUserId =:toUserId", nativeQuery = true)
    void mUnfollow(int fromUserId , int toUserId);
}
