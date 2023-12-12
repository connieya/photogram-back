package com.cos.photogramstart.domain.post.repository.querydsl;

import com.cos.photogramstart.domain.post.entity.Post;
import com.cos.photogramstart.web.dto.post.PostPopularDto;
import com.cos.photogramstart.web.dto.post.UserImageResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.cos.photogramstart.domain.folllow.QFollow.*;
import static com.cos.photogramstart.domain.post.QImage.image;
import static com.cos.photogramstart.domain.likes.QLikes.likes;

public class PostRepositoryImpl implements PostRepositoryQuertdsl {

    private final JPAQueryFactory queryFactory;

    public PostRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Post> getStory(int principalId) {
        List<Post> storys = queryFactory
                .selectFrom(image)
                .where(image.user.id.in(JPAExpressions.select(follow.toUser.id).from(follow)
                        .where(follow.fromUser.id.eq(principalId)))).orderBy(image.createDate.desc()).fetch();
        return storys;
    }



    @Override
    public List<PostPopularDto> popular() {
        return  queryFactory
                .select(Projections.fields(PostPopularDto.class,
                        image.id
                        , image.caption
                        , image.postImageUrl
                        , image.user.username
                        , image.user.profileImageUrl
                        , image.user.id.as("userId")
                        , likes.image.id.count().as("likeCount")))
                .from(image)
                .innerJoin(likes)
                .on(image.id.eq(likes.image.id))
                .groupBy(likes.image.id)
                .orderBy(likes.image.id.count().desc(), image.createDate.desc())
                .limit(9)
                .fetch();
    }

    @Override
    public List<UserImageResponse> selectUserImage(int userId) {
        return queryFactory
                .select(Projections.fields(
                        UserImageResponse.class,
                        image.postImageUrl
                        ,image.caption
                        ,image.id.as("imageId")
                        ,likes.id.count().as("likeCount")))
                .from(image)
                .leftJoin(likes)
                .on(image.id.eq(likes.image.id))
                .where(image.user.id.eq(userId))
                .groupBy(image.id)
                .fetch();
    }

}
