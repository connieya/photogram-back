package com.cos.photogramstart.domain.folllow;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

public class FollowRepositoryImpl implements FollowRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public FollowRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public void Follow(int fromUserId, int toUserId) {

    }
}
