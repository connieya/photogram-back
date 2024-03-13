package com.cos.photogramstart.domain.user.infrastructure;

import com.cos.photogramstart.domain.user.application.result.UserProfileResult;
import com.cos.photogramstart.domain.user.domain.User;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.cos.photogramstart.domain.user.domain.QUser.*;


@RequiredArgsConstructor
public class UserRepositoryImpl implements UseCustomRepository {

    private final JPAQueryFactory queryFactory;


    @Override
    public UserProfileResult findUserProfile(User loginUser , String username) {
        return queryFactory
                .select(Projections.fields(UserProfileResult.class,
                        user.username,
                        user.name,
                        user.website,
                        user.image,
                        user.eq(loginUser).as("pageOwner"))
                )
                .from(user)
                .where(user.username.eq(username))
                .fetchOne();

    }
}
