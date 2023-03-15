package com.cos.photogramstart.domain.image;

import com.cos.photogramstart.domain.likes.QLikes;
import com.cos.photogramstart.web.dto.image.ImagePopularDto;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
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
import static com.cos.photogramstart.domain.likes.QLikes.*;
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

    @Test
    public void popular() {
        List<Tuple> fetch = queryFactory
                .select(likes.image.id, likes.image.id.count())
                .from(likes)
                .groupBy(likes.image.id)
                .orderBy(likes.image.id.count().desc())
                .fetch();

        List<ImagePopularDto> imageList = queryFactory
                .select(Projections.fields(ImagePopularDto.class,
                        image.id, image.caption , image.postImageUrl, image.user.username, image.user.id.as("userId")
                        , likes.image.id.count().as("likeCount")))
                .from(image)
                .innerJoin(likes)
                .on(image.id.eq(likes.image.id))
                .groupBy(likes.image.id)
                .orderBy(likes.image.id.count().desc(), image.createDate.desc())
                .limit(9)
                .fetch();

        for (int i = 0; i < imageList.size(); i++) {
            System.out.println("imageList " +i+ " => " + imageList.get(i));

        }

    }
}