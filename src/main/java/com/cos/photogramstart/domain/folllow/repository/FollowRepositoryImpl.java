package com.cos.photogramstart.domain.folllow.repository;

import com.cos.photogramstart.domain.folllow.QFollow;
import com.cos.photogramstart.web.dto.follow.FollowDto;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
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

    @Override
    public List<FollowDto> followingList(int principalId ,int pageUser) {
        QFollow followSub = new QFollow("followSub");
        return queryFactory
                .select(Projections.fields(FollowDto.class,
                        user.id, user.username, user.profileImageUrl,
                        ExpressionUtils.as(JPAExpressions.select().from(followSub)
                                .where(followSub.fromUser.id.eq(principalId).and(followSub.toUser.id.eq(user.id))).exists(), "followState"),
                        user.id.eq(principalId).as("equalUserState")

                ))
                .from(follow)
                .innerJoin(user)
                .on(user.id.eq(follow.toUser.id))
                .where(follow.fromUser.id.eq(pageUser))
                .fetch();
    }

    @Override
    public List<FollowDto> followerList(int principalId, int pageUser) {
        QFollow followSub = new QFollow("followSub");
        return queryFactory
                .select(Projections.fields(FollowDto.class,
                        user.id, user.username, user.profileImageUrl,
                        ExpressionUtils.as(JPAExpressions.select().from(followSub)
                                .where(followSub.fromUser.id.eq(principalId).and(followSub.toUser.id.eq(user.id))).exists(), "followState"),
                        user.id.eq(principalId).as("equalUserState")

                ))
                .from(follow)
                .innerJoin(user)
                .on(user.id.eq(follow.fromUser.id))
                .where(follow.toUser.id.eq(pageUser))
                .fetch();
    }
}
