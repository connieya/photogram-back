package com.cos.photogramstart.domain.image;

import com.cos.photogramstart.domain.folllow.QFollow;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.cos.photogramstart.domain.folllow.QFollow.*;

public class ImageRepositoryImpl implements ImageRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public ImageRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Image> getStory(int principalId) {
        JPAQuery<Integer> followingList = queryFactory
                .select(follow.toUser.id)
                .from(follow)
                .where(follow.fromUser.id.eq(principalId));
        return null;
    }
}
