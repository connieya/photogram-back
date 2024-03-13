package com.cos.photogramstart.domain.comment.infrastructure;

import com.cos.photogramstart.domain.comment.application.CommentResult;
import com.cos.photogramstart.domain.comment.application.QCommentResult;
import com.cos.photogramstart.domain.comment.domain.QComment;
import com.cos.photogramstart.domain.user.domain.QUser;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.cos.photogramstart.domain.comment.domain.QComment.*;


@Repository
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPQLQueryFactory queryFactory;

    public CommentRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<CommentResult> findByPostId(Long postId) {
        return queryFactory
                .select(new QCommentResult(
                        comment.id,
                        comment.content,
                        comment.user.id,
                        comment.user.username
                ))
                .from(comment)
                .where(comment.post.id.eq(postId))
                .fetch();

    }
}
