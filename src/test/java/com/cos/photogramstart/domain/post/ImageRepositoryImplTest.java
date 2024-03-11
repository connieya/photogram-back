package com.cos.photogramstart.domain.post;

import com.cos.photogramstart.domain.post.domain.Post;
import com.cos.photogramstart.domain.post.presentation.PostPopularDto;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static com.cos.photogramstart.domain.folllow.QFollow.follow;
import static com.cos.photogramstart.domain.post.QImage.*;
import static com.cos.photogramstart.domain.likes.QLikes.*;

@SpringBootTest
@Transactional
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
        List<Post> storys = queryFactory
                .selectFrom(image)
                .where(image.user.id.in(followingList)).fetch();
        for (Post story : storys) {
            System.out.println("story = " + story);
        }
    }
    @Test
    public void getStory2(){
        int principalId = 1;
        List<Post> storys = queryFactory
                .selectFrom(image)
                .where(image.user.id.in(JPAExpressions.select(follow.toUser.id).from(follow)
                        .where(follow.fromUser.id.eq(principalId)))).orderBy(image.createDate.desc()).fetch();
        for (Post story : storys) {
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

        List<PostPopularDto> imageList = queryFactory
                .select(Projections.fields(PostPopularDto.class,
                        image.id, image.caption , image.postImageUrl, image.user
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