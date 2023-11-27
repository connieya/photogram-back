package com.cos.photogramstart.domain.comment.repository;

import com.cos.photogramstart.web.dto.comment.CommentResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.cos.photogramstart.domain.comment.QComment.*;
import static com.cos.photogramstart.domain.user.QUser.*;

public class CommentRepositoryImpl implements CommentRepositoryCustom{

    private final JPQLQueryFactory queryFactory;

    public CommentRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<CommentResponseDto> findByImageId(int imageId) {
        return queryFactory
                .select(Projections.fields(CommentResponseDto.class,
                        comment.id.as("contentId"),comment.content,user.id.as("userId"),user.username))
                .from(comment)
                .join(user)
                .on(comment.user.id.eq(user.id))
                .where(comment.image.id.eq(imageId))
                .fetch();
    }
}
