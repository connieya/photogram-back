package com.cos.photogramstart.domain.folllow.infrastructure;

import com.cos.photogramstart.domain.folllow.application.FollowResult;
import com.cos.photogramstart.domain.folllow.domain.QFollow;
import com.cos.photogramstart.domain.user.domain.QUser;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;

import static com.cos.photogramstart.domain.folllow.domain.QFollow.*;
import static com.cos.photogramstart.domain.user.domain.QUser.*;


@Repository
public class FollowRepositoryImpl implements FollowCustomRepository {

    private final JPAQueryFactory queryFactory;

    public FollowRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Long followingCount(Long pageUserId) {
        return queryFactory.
                select()
                .from(follow)
                .where(follow.fromUser.id.eq(pageUserId))
                .fetchCount();

    }

    @Override
    public Long followerCount(Long pageUserId) {
        return queryFactory.
                select()
                .from(follow)
                .where(follow.toUser.id.eq(pageUserId))
                .fetchCount();

    }

    @Override
    public List<FollowResult> followingList(Long loginUserId, Long pageUser) {
        QFollow followSub = new QFollow("followSub");
        return queryFactory
                .select(Projections.fields(FollowResult.class,
                        user.username, user.image.imageUrl,
                        ExpressionUtils.as(JPAExpressions.select().from(followSub)
                                .where(followSub.fromUser.id.eq(loginUserId).and(followSub.toUser.id.eq(user.id))).exists(), "followState"),
                        user.id.eq(loginUserId).as("isCurrentUser")

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
                .select(
                        Projections.fields(FollowResult.class,
                                user.username, user.image.imageUrl,
                                ExpressionUtils.as(JPAExpressions.select().from(followSub)
                                        .where(followSub.fromUser.id.eq(loginUserId).and(followSub.toUser.id.eq(user.id))).exists(), "followState"),
                                user.id.eq(loginUserId).as("isCurrentUser")

                        ))
                .from(follow)
                .innerJoin(user)
                .on(user.id.eq(follow.fromUser.id))
                .where(follow.toUser.id.eq(pageUser))
                .fetch();
    }
}
