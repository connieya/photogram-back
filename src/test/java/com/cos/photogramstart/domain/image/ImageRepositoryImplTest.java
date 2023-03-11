package com.cos.photogramstart.domain.image;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static com.cos.photogramstart.domain.folllow.QFollow.follow;
import static com.cos.photogramstart.domain.image.QImage.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ImageRepositoryImplTest {
    @PersistenceContext
    EntityManager em;
    JPAQueryFactory queryFactory;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);
    }
    @Test
    public void followingList() {
        int principalId = 2;
        List<Integer> followingList = queryFactory
                .select(follow.toUser.id)
                .from(follow)
                .where(follow.fromUser.id.eq(principalId))
                .fetch();
        for (Integer integer : followingList) {
            System.out.println("following user = " + integer);
        }
    }

    @Test
    public void getStory(){
        int principalId = 1;
        List<Integer> followingList = queryFactory
                .select(follow.toUser.id)
                .from(follow)
                .where(follow.fromUser.id.eq(principalId))
                .fetch();
        List<Image> storys = queryFactory
                .selectFrom(image)
                .where(image.user.id.in(followingList)).fetch();
        for (Image story : storys) {
            System.out.println("story = " + story);
        }
    }
    @Test
    public void getStory2(){
        int principalId = 1;
        List<Image> storys = queryFactory
                .selectFrom(image)
                .where(image.user.id.in(JPAExpressions.select(follow.toUser.id).from(follow)
                        .where(follow.fromUser.id.eq(principalId)))).orderBy(image.createDate.desc()).fetch();
        for (Image story : storys) {
            System.out.println("story = " + story);
        }
    }
}