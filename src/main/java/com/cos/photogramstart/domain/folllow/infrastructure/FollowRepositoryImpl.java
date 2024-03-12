package com.cos.photogramstart.domain.folllow.infrastructure;

import com.cos.photogramstart.domain.folllow.QFollow;
import com.cos.photogramstart.domain.folllow.application.FollowResult;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

import java.util.List;

import static com.cos.photogramstart.domain.folllow.QFollow.follow;
import static com.cos.photogramstart.domain.user.QUser.user;

public class FollowRepositoryImpl implements FollowCustomRepository {

    private final JPAQueryFactory queryFactory;

    public FollowRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public int followState(Long principalId, int pageUserId) {
        return (int)queryFactory
                .select()
                .from(follow)
                .where(follow.fromUser.id.eq(Math.toIntExact(principalId)).and(follow.toUser.id.eq(pageUserId))).fetchCount();
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
    public List<FollowResult> followingList(int principalId , int pageUser) {
        QFollow followSub = new QFollow("followSub");
        return queryFactory
                .select(Projections.fields(FollowResult.class,
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
    public List<FollowResult> followerList(Long loginUserId, Long pageUser) {
        QFollow followSub = new QFollow("followSub");
        return queryFactory
                .select(Projections.fields(FollowResult.class,
                        user.id, user.username, user.profileImageUrl,
                        ExpressionUtils.as(JPAExpressions.select().from(followSub)
                                .where(followSub.fromUser.id.eq(Math.toIntExact(loginUserId)).and(followSub.toUser.id.eq(user.id))).exists(), "followState"),
                        user.id.eq(Math.toIntExact(loginUserId)).as("isCurrentUser")

                ))
                .from(follow)
                .innerJoin(user)
                .on(user.id.eq(follow.fromUser.id))
                .where(follow.toUser.id.eq(Math.toIntExact(pageUser)))
                .fetch();
    }
}
