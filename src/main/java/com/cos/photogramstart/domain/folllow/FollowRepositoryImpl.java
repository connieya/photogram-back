package com.cos.photogramstart.domain.folllow;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

public class FollowRepositoryImpl implements FollowRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public FollowRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public int followState(int principalId, int pageUserId) {
        return 0;
    }

    @Override
    public int followingCount(int pageUserId) {
        return 0;
    }

    @Override
    public int followerCount(int pageUserId) {
        return 0;
    }
}
