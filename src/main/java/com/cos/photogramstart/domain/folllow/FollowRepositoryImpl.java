package com.cos.photogramstart.domain.folllow;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.web.dto.follow.FollowDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

import java.util.List;

import static com.cos.photogramstart.domain.folllow.QFollow.follow;
import static com.cos.photogramstart.domain.user.QUser.user;

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

    public List<User> followingList(int principalId ,int pageUser) {
//        queryFactory
//                .select()
//                .from(user)
//                .where(follow.fromUser.id.eq(principalId))
        return queryFactory
                .select(user)
                .from(follow)
                .innerJoin(user)
                .on(user.id.eq(follow.toUser.id))
                .where(follow.fromUser.id.eq(pageUser))
                .fetch();
    }
}
