package com.cos.photogramstart.domain.folllow.application;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@Getter
@NoArgsConstructor
@Builder
public class FollowResult {
    private String username;
    private String profileImageUrl;
    private boolean followState;
    private boolean isCurrentUser;

    @QueryProjection
    public FollowResult(String username, String profileImageUrl, boolean followState, boolean isCurrentUser) {
        this.username = username;
        this.profileImageUrl = profileImageUrl;
        this.followState = followState;
        this.isCurrentUser = isCurrentUser;
    }
}
