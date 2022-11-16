package com.cos.photogramstart.domain.subscribe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubscribeRepository extends JpaRepository<Subscribe,Integer> {

    @Modifying
    @Query(value = "insert into subscribe(fromUserid ,toUserId , createDate) values(:fromUserId, :toUserId , now())", nativeQuery = true)
    void mSubscribe(int fromUserId, int toUserId);

    @Modifying
    @Query(value = "delete from  subscribe where fromUserId =:fromUserId and toUserId =:toUserId", nativeQuery = true)
    void mUnSubscribe(int fromUserId , int toUserId);
}
