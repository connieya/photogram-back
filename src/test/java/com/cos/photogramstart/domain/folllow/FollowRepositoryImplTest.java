package com.cos.photogramstart.domain.folllow;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static com.cos.photogramstart.domain.folllow.QFollow.*;
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


}