package com.cos.photogramstart.domain.image;

import com.cos.photogramstart.domain.folllow.QFollow;
import com.cos.photogramstart.domain.likes.QLikes;
import com.cos.photogramstart.web.dto.image.ImageData;
import com.cos.photogramstart.web.dto.image.ImagePopularDto;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.cos.photogramstart.domain.folllow.QFollow.*;
import static com.cos.photogramstart.domain.image.QImage.image;
import static com.cos.photogramstart.domain.likes.QLikes.likes;

public class ImageRepositoryImpl implements ImageRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ImageRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Image> getStory(int principalId) {
        List<Image> storys = queryFactory
                .selectFrom(image)
                .where(image.user.id.in(JPAExpressions.select(follow.toUser.id).from(follow)
                        .where(follow.fromUser.id.eq(principalId)))).orderBy(image.createDate.desc()).fetch();
        return storys;
    }

//    @Override
//    public List<ImageData> getStory(int principalId) {
//        List<ImageData> storyData = queryFactory
//                .select(Projections.fields(ImageData.class,
//                        image,
//                        ExpressionUtils.as(JPAExpressions.select(likes.id.count()).from(likes).where(likes.image.id.eq(image.id)),"likeCount"),
//                        ExpressionUtils.as(JPAExpressions.select().from(likes).where(likes.user.id.eq(principalId)).exists()
//                                , "likeState")
//                ))
//                .from(image)
//                .where(image.user.id.in(JPAExpressions.select(follow.toUser.id).from(follow)
//                        .where(follow.fromUser.id.eq(principalId)))).orderBy(image.createDate.desc()).fetch();
//        for (ImageData storyDatum : storyData) {
//            System.out.println("@@@@@ storyDatum = " + storyDatum.getImage().getUser().getProfileImageUrl());
//        }
//        return storyData;
//    }

    @Override
    public List<ImagePopularDto> popular() {
        return  queryFactory
                .select(Projections.fields(ImagePopularDto.class,
                        image.id, image.caption , image.postImageUrl, image.user
                        , likes.image.id.count().as("likeCount")))
                .from(image)
                .innerJoin(likes)
                .on(image.id.eq(likes.image.id))
                .groupBy(likes.image.id)
                .orderBy(likes.image.id.count().desc(), image.createDate.desc())
                .limit(9)
                .fetch();
    }

}
