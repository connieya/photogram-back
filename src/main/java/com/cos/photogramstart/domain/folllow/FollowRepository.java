package com.cos.photogramstart.domain.folllow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FollowRepository extends JpaRepository<Follow,Integer> {

    @Modifying
    @Query(value = "insert into follow(fromUserid ,toUserId , createDate) values(:fromUserId, :toUserId , now())", nativeQuery = true)
    void mFollow(int fromUserId, int toUserId);

    @Modifying
    @Query(value = "delete from  follow where fromUserId =:fromUserId and toUserId =:toUserId", nativeQuery = true)
    void mUnfollow(int fromUserId , int toUserId);

    @Query(value = "SELECT count(*) FROM follow WHERE fromUserId =:principalId and toUserId= :pageUserId" , nativeQuery = true)
    int mFollowState(int principalId, int pageUserId);

    @Query(value = "SELECT count(*) FROM follow WHERE fromUserId =:pageUserId" , nativeQuery = true)
    int mFollowingCount(int pageUserId);

    @Query(value = "SELECT count(*) FROM follow WHERE toUserId =:pageUserId" , nativeQuery = true)
    int mFollowerCount(int pageUserId);

}
