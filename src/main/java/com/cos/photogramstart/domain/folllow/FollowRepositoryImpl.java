package com.cos.photogramstart.domain.folllow;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

import static com.cos.photogramstart.domain.folllow.QFollow.follow;

public class FollowRepositoryImpl implements FollowRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public FollowRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public int followState(int principalId, int pageUserId) {
        return (int)queryFactory
                .select()
                .from(follow)
                .where(follow.fromUser.id.eq(principalId).and(follow.toUser.id.eq(pageUserId))).fetchCount();
    }

    @Override
    public int followingCount(int pageUserId) {
        return (int) queryFactory.
                select()
                .from(follow)
                .where(follow.fromUser.id.eq(pageUserId))
                .fetchCount();
    }

    @Override
    public int followerCount(int pageUserId) {
        return (int) queryFactory.
                select()
                .from(follow)
                .where(follow.toUser.id.eq(pageUserId))
                .fetchCount();
    }
}
