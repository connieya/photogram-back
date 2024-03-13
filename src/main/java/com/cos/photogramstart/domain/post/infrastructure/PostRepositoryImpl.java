package com.cos.photogramstart.domain.post.infrastructure;

import com.cos.photogramstart.domain.comment.application.CommentResult;
import com.cos.photogramstart.domain.comment.application.QCommentResult;
import com.cos.photogramstart.domain.post.application.PostResult;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.cos.photogramstart.domain.comment.domain.QComment.*;
import static com.cos.photogramstart.domain.folllow.domain.QFollow.*;
import static com.cos.photogramstart.domain.post.domain.QPost.*;
import static com.cos.photogramstart.domain.post.domain.QPostLike.*;

@Repository
public class PostRepositoryImpl implements PostCustomRepository {

    private final JPAQueryFactory queryFactory;

    public PostRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<PostResult> findAll(Long loginUserId) {
        return queryFactory
                .select(Projections.fields(PostResult.class,
                        post.id.as("postId"),
                        post.postImage.image.imageUrl.as("postImageUrl"),
                        post.caption.as("content"),
                        post.user.id.as("userId"),
                        post.user.username,
                        post.user.image.imageUrl.as("profileImageUrl"),
                        ExpressionUtils.as(
                                JPAExpressions.selectFrom(postLike)
                                        .where(postLike.user.id.eq(loginUserId)
                                                .and(postLike.post.id.eq(post.id)))
                                        .exists(),
                                "likeState"
                        ),
                        post.postLikes.size().as("likeCount")
                        ))
                .from(post)
                .where(post.user.id.in(
                        JPAExpressions
                                .select(follow.toUser.id)
                                .from(follow)
                                .where(follow.fromUser.id.eq(loginUserId))
                ))
                .fetch();
    }
}
