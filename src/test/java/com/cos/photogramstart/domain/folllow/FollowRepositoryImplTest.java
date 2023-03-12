package com.cos.photogramstart.domain.folllow;

import com.cos.photogramstart.domain.user.QUser;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.web.dto.follow.FollowDto;
import com.querydsl.core.QueryFactory;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.math.BigInteger;
import java.util.List;

import static com.cos.photogramstart.domain.folllow.QFollow.*;
import static com.cos.photogramstart.domain.user.QUser.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class FollowRepositoryImplTest {

    @PersistenceContext
    EntityManager em;
    JPAQueryFactory queryFactory;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);
    }

    @Test
    public void followState() {
        int principalId = 1;
        int pageUserId = 7;
        long l = queryFactory
                .select()
                .from(follow)
                .where(follow.fromUser.id.eq(principalId).and(follow.toUser.id.eq(pageUserId))).fetchCount();
        assertThat(l).isEqualTo(0);
    }

    @Test
    public void followingCount() {
        int pageUser = 1;
        long count = queryFactory.
                select()
                .from(follow)
                .where(follow.fromUser.id.eq(pageUser))
                .fetchCount();

        assertThat(count).isEqualTo(4);

    }
    @Test
    public void followerCount() {
        int pageUser = 1;
        long count = queryFactory.
                select()
                .from(follow)
                .where(follow.toUser.id.eq(pageUser))
                .fetchCount();

        assertThat(count).isEqualTo(3);
    }

    @Test
    public void followingList(){
        int principalId = 1;
        int pageUser = 2;
        QFollow followSub = new QFollow("followSub");
        List<FollowDto> followDtos = queryFactory
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

        for (FollowDto followDto : followDtos) {
            System.out.println("followDto = " + followDto);
        }

    }


}