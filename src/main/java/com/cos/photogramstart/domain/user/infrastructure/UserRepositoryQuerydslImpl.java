package com.cos.photogramstart.domain.user.infrastructure;

import com.cos.photogramstart.domain.user.application.result.UserProfileResult;
import com.cos.photogramstart.domain.user.dto.QUserProfileResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.cos.photogramstart.domain.user.QUser.*;

@RequiredArgsConstructor
public class UserRepositoryQuerydslImpl implements UserRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;

    @Override
    public UserProfileResult findUserProfile(Long loginUserId, String username) {
        return queryFactory
                .select(new QUserProfileResponse(
                                user.username,
                                user.name,
                                user.website,
                                user.image,
                                user.id.eq(loginUserId.intValue())
                        )
                )
                .from(user)
                .where(user.username.eq(username))
                .fetchOne();

    }
}
